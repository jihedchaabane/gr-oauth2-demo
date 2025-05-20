package com.chj.gr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;

import feign.RequestInterceptor;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(OAuth2AuthorizedClientService clientService) {
        return requestTemplate -> {
            OAuth2AuthorizedClient client = clientService.loadAuthorizedClient("feign", "feign");
            if (client != null && client.getAccessToken() != null) {
                requestTemplate.header("Authorization", "Bearer " + client.getAccessToken().getTokenValue());
            } else {
                throw new IllegalStateException("No OAuth2 access token found for registeredClient 'feign'");
            }
        };
    }
	
}


