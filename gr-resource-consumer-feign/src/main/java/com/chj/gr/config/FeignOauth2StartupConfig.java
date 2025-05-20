package com.chj.gr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.ClientAuthorizationException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizationContext;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class FeignOauth2StartupConfig implements ApplicationRunner {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Create an Authentication object with the client ID as the principal
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "feign",
                null,
                Collections.emptyList()
        );

        // Create an OAuth2AuthorizedClientProvider for client credentials flow
        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build();

        // Retrieve the client registration for 'feign'
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("feign");
        if (clientRegistration == null) {
            throw new IllegalStateException("Client registration 'feign' not found");
        }

        // Create an OAuth2AuthorizationContext
        OAuth2AuthorizationContext authorizationContext = OAuth2AuthorizationContext
                .withClientRegistration(clientRegistration)
                .principal(authentication)
                .build();

        try {
            OAuth2AuthorizedClient authorizedClient = authorizedClientProvider.authorize(authorizationContext);

            if (authorizedClient != null) {
                // Save the authorized client with the Authentication object
                authorizedClientService.saveAuthorizedClient(authorizedClient, authentication);
            } else {
                throw new IllegalStateException("Failed to obtain OAuth2 token for client 'feign' during startup");
            }
        } catch (ClientAuthorizationException e) {
            throw new IllegalStateException("Failed to authorize client 'feign' during startup", e);
        }
    }
}