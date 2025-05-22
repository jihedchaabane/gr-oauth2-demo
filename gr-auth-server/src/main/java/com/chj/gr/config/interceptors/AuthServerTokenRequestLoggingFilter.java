package com.chj.gr.config.interceptors;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
/**
 * Juste après le filtre de Spring Security.
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class AuthServerTokenRequestLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthServerTokenRequestLoggingFilter.class);
    
	private void printParams(HttpServletRequest request) {
		java.util.Enumeration<String> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paramName = enu.nextElement();
			logger.debug("PARAM: " + paramName + ": " + request.getParameter(paramName));
		}
	}
	
	private void printParams2(HttpServletRequest request) {
        request.getParameterMap().forEach((key, values) -> 
            logger.info("PARAM: {} : {}", key, String.join(",", values))
        );
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	logger.info("Reception d'une requête d'authorisation : {}", request.getRequestURI());
    	String userAgent = request.getHeader("User-Agent");
    	logger.info("User-Agent: {}", userAgent != null ? userAgent : "none");
    	logger.info("Source IP:PORT {}:{}", request.getRemoteAddr(), request.getRemotePort());
    	
    	if (   request.getRequestURI().endsWith("/oauth2/token") 
    		|| 	request.getRequestURI().endsWith("/oauth2/jwks")
    		|| 	request.getRequestURI().contains("/actuator/")) {
    		logger.info("Requête de token reçue, avec les parametres :");
    		printParams(request);

    		String authHeader = request.getHeader("Authorization");
    		
    		if (authHeader != null && authHeader.startsWith("Basic ")) {
                // Decode the Basic Auth header
                String base64Credentials = authHeader.substring("Basic ".length()).trim();
                String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
                String[] clientCredentials = credentials.split(":", 2);
                String clientId = clientCredentials.length > 0 ? clientCredentials[0] : "unknown";
                String clientSecret = clientCredentials.length > 1 ? clientCredentials[1] : "unknown";

                logger.info("En-tête Authorization détecté pour la requête de token.");
                logger.info("Client ID: {}", clientId);
                logger.info("Client Secret: {}", "**************");
            } else {
                logger.info("No Basic Authorization header found.");
            }
        }
    	logger.info("Invocation du filtre : {}", filterChain.getClass().getCanonicalName());
        filterChain.doFilter(request, response);
        logger.info("Response.Status : {}", response.getStatus());
        
        if ((request.getRequestURI().endsWith("/oauth2/token") 
        		|| request.getRequestURI().endsWith("/oauth2/jwks")
        		|| 	request.getRequestURI().contains("/actuator/"))) {
        	if (response.getStatus() == 200) {
        		logger.info("Token généré avec succès.. {}", request.getRequestURI());
			} else {
				logger.info("Echec de la génération d'un token pour {}", request.getRequestURI());
			}
        }
        logger.info("----------------------------------------------------------");
    }
}
