//package com.chj.gr.config.interceptors;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuthServerAuthenticationFailureHandler implements AuthenticationFailureHandler {
//    private static final Logger logger = LoggerFactory.getLogger(AuthServerAuthenticationFailureHandler.class);
//
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//                                       AuthenticationException exception) throws IOException, ServletException {
//        String clientId 		= request.getParameter("client_id");
//        String clientSecret 	= request.getParameter("client_secret");
//        String scopes 			= request.getParameter("scope");
//        String grantType 		= request.getParameter("grant_type");
//
//        logger.error("Échec de l'authentification pour clientId={}, clientSecret= {}, scopes={}, erreur={}",
//                clientId != null 		? clientId 		: "NON FOURNI",
//                clientSecret != null 	? "********" 	: "NON FOURNI",
//                scopes != null 			? scopes 		: "NON FOURNI",
//                grantType != null 		? grantType 	: "NON FOURNI",
//                exception.getMessage());
//
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.getWriter().write("{\"error\": \"" + exception.getMessage() + "\"}");
//    }
//}
