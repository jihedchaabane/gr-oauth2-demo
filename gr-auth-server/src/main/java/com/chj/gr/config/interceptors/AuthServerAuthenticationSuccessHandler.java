//package com.chj.gr.config.interceptors;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class AuthServerAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//    private static final Logger logger = LoggerFactory.getLogger(AuthServerAuthenticationSuccessHandler.class);
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//        if (authentication instanceof OAuth2AccessTokenAuthenticationToken) {
//        	
//            OAuth2AccessTokenAuthenticationToken tokenAuth = (OAuth2AccessTokenAuthenticationToken) authentication;
//            logger.info("Token généré avec succès : clientId={}, scopes={}, tokenType={}",
//                    tokenAuth.getRegisteredClient().getClientId(),
//                    tokenAuth.getAccessToken().getScopes(),
//                    tokenAuth.getAccessToken().getTokenType().getValue());
//        }
//    }
//}
