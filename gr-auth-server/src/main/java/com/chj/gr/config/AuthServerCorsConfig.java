package com.chj.gr.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class AuthServerCorsConfig {

	/**
     * Securing OPTIONS /oauth2/token indique que le serveur reçoit une requête OPTIONS, qui est une requête CORS preflight envoyée
     * par le navigateur (via Swagger UI) avant la requête POST.
     * 
     * Swagger UI, exécuté dans le navigateur (sur http://localhost:8081 ou http://localhost:8082), envoie une requête CORS vers http://localhost:8764/oauth2/token. 
     * 
     * Si le serveur d'autorisation ne retourne pas les en-têtes CORS appropriés (comme Access-Control-Allow-Origin), 
     * 		le navigateur rejette la réponse, entraînant une erreur 403.
     * 
     * Spring Security ou le serveur d'autorisation peut ne pas gérer correctement les requêtes OPTIONS, qui sont nécessaires pour les requêtes CORS:
     * 		donc, ci dessous la configuration correspondante.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        /**
         *  WORKS FINE.
         */
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowCredentials(false); // Nécessaire pour le motif "*"
        /** */
        /**
         * WORKS FINE TOO.
         */
//        configuration.setAllowedOriginPatterns(Arrays.asList("[*]"));
//        configuration.setAllowCredentials(false); // Nécessaire pour le motif "[*]"
        /** */
        /**
         * WORKS FINE TOO.
         */
//        configuration.setAllowedOrigins(Arrays.asList(
//        		"http://localhost:8081", 	// swagger Authorize : gr-oauth2-swagger-ms1
//        		"http://localhost:8082",	// swagger Authorize : gr-oauth2-swagger-ms2
//        		"http://localhost:8766" 	// swagger Authorize : gr-conf-swagger-aggregator
//        ));
//        configuration.setAllowCredentials(true);
        /** */
        /**
         * WORKS FINE TOO.
         */
//        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:*"));
//        configuration.setAllowCredentials(true); // Nécessaire pour le motif "http://localhost:*"
        /** */
        
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
