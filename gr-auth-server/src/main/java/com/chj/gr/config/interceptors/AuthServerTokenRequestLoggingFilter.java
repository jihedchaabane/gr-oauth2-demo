package com.chj.gr.config.interceptors;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthServerTokenRequestLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthServerTokenRequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	logger.info("Reception d'une requête d'authorisation.");
    	if (request.getRequestURI().endsWith("/oauth2/token")) {
        	
            String clientId 		= request.getParameter("client_id");
            String clientSecret 	= request.getParameter("client_secret");
            String scopes 			= request.getParameter("scope");
            String grantType 		= request.getParameter("grant_type");

            logger.info("Requête de token reçue : clientId={}, clientSecret={}, grantType={}, scopes={}",
                    clientId != null 		? clientId 		: "NON FOURNI",
                    clientSecret != null 	? "********" 	: "NON FOURNI",
                    scopes != null 			? scopes 		: "NON FOURNI",
            		grantType != null 		? grantType 	: "NON FOURNI");

            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Basic ")) {
                logger.info("En-tête Authorization détecté pour la requête de token!");
            }
        }

        filterChain.doFilter(request, response);
        
        if (request.getRequestURI().endsWith("/oauth2/token") && response.getStatus() == 200) {
            logger.info("Token généré avec succès pour clientId={}, scopes={}",
                    request.getParameter("client_id") != null ? request.getParameter("client_id") : "NON FOURNI",
                    request.getParameter("scope") != null ? request.getParameter("scope") : "NON FOURNI");
        }
    }
}
