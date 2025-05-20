package com.chj.gr.config.interceptors;

import java.io.IOException;

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
@Order(Ordered.HIGHEST_PRECEDENCE + 1) // Juste après le filtre de Spring Security
public class AuthServerTokenRequestLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthServerTokenRequestLoggingFilter.class);
    
	private void printParams(HttpServletRequest request) {
		java.util.Enumeration<String> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paramName = enu.nextElement();
			logger.debug("PARAM: " + paramName + ": " + request.getParameter(paramName));
		}
	}
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	logger.info("Reception d'une requête d'authorisation : {}", request.getRequestURI());
    	if (request.getRequestURI().endsWith("/oauth2/token") || request.getRequestURI().endsWith("/oauth2/jwks")) {
    		logger.info("Requête de token reçue, avec les parametres :");
    		printParams(request);

    		String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Basic ")) {
                logger.info("En-tête Authorization détecté pour la requête de token..");
            }
        }
    	logger.info("Invocation du filtre : {}", filterChain.getClass().getCanonicalName());
        filterChain.doFilter(request, response);
        logger.info("Response.Status : {}", response.getStatus());
        
        if ((request.getRequestURI().endsWith("/oauth2/token") || request.getRequestURI().endsWith("/oauth2/jwks")) && response.getStatus() == 200) {
            logger.info("Token généré avec succès.. {}", request.getRequestURI());
        }
        logger.info("----------------------------------------------------------");
    }
}
