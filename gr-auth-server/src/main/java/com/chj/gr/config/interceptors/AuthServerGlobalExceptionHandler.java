package com.chj.gr.config.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AuthServerGlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(AuthServerGlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleUnexpectedException(Exception ex) {
        logger.error("Erreur inattendue dans le serveur d'authorisation!", ex);
        return "{\"error\": \"Erreur interne du serveur\"}";
    }
}
