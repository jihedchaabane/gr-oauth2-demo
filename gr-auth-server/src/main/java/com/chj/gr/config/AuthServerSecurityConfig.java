package com.chj.gr.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class AuthServerSecurityConfig {
	private static final Logger logger = LoggerFactory.getLogger(AuthServerSecurityConfig.class);
	
	private CorsConfigurationSource corsConfigurationSource;
	
	public AuthServerSecurityConfig(CorsConfigurationSource corsConfigurationSource) {
		this.corsConfigurationSource = corsConfigurationSource;
		logger.info("AuthServerSecurityConfig initialisée");
	}

	@Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(400, "Authentication error: " + authException.getMessage());
                        })
                )
                .build();
    }
	
	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/oauth2/token").permitAll()
                                .anyRequest().permitAll()
                );
        return http.build();
    }
	
    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder()
              /**
        		.issuer("http://localhost:8764")
        		@TODO try to replace it with eureka discovery alternative.	
				.issuer("http://GR-AUTH-SERVER")
               */
        		.issuer("http://localhost:8764")
        		.tokenEndpoint("/oauth2/token")
                .build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient ms1Client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gr-ms1-resource")
                .clientSecret("{noop}secret1")
                .scope("ms1.read")
                .scope("ms2.read")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                .tokenSettings(
                		/**
                		 * Expiration de 1 jour.
                		 */
                		TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(1)).build()
                )
                .build();
        logger.info("Client créé : clientId={}, scopes={}, grantTypes={}",
        		ms1Client.getClientId(),
        		ms1Client.getScopes(),
        		ms1Client.getAuthorizationGrantTypes());
        /**
         * 
         */
        RegisteredClient ms2Client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gr-ms2-resource")
                .clientSecret("{noop}secret2")
                .scope("ms2.read")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                .tokenSettings(
                		/**
                		 * Expiration de 1 jour.
                		 */
                		TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(1)).build()
                )
                .build();
        logger.info("Client créé : clientId={}, scopes={}, grantTypes={}",
        		ms2Client.getClientId(),
        		ms2Client.getScopes(),
        		ms2Client.getAuthorizationGrantTypes());
        
        RegisteredClient restTemplateClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gr-resource-consumer-resttemplate")
                .clientSecret("{noop}consumer-resttemplate")
                .scope("ms1.read")
                .scope("ms2.read")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                .tokenSettings(
                		/**
                		 * Expiration de 1 jour.
                		 */
                		TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(1)).build()
                )
                .build();
        logger.info("Client créé : clientId={}, scopes={}, grantTypes={}",
        		restTemplateClient.getClientId(),
        		restTemplateClient.getScopes(),
        		restTemplateClient.getAuthorizationGrantTypes());
        
        RegisteredClient webclient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gr-resource-consumer-webclient")
                .clientSecret("{noop}consumer-webclient")
                .scope("ms1.read")
                .scope("ms2.read")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                .tokenSettings(
                		/**
                		 * Expiration de 1 jour.
                		 */
                		TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(1)).build()
                )
                .build();
        logger.info("Client créé : clientId={}, scopes={}, grantTypes={}",
        		webclient.getClientId(),
        		webclient.getScopes(),
        		webclient.getAuthorizationGrantTypes());
        
        RegisteredClient feignClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gr-resource-consumer-feign")
                .clientSecret("{noop}consumer-feign")
                .scope("ms1.read")
                .scope("ms2.read")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                .tokenSettings(
                		/**
                		 * Expiration de 1 jour.
                		 */
                		TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(1)).build()
                )
                .build();
        logger.info("Client créé : clientId={}, scopes={}, grantTypes={}",
        		feignClient.getClientId(),
        		feignClient.getScopes(),
        		feignClient.getAuthorizationGrantTypes());
        
        return new InMemoryRegisteredClientRepository(ms1Client, ms2Client, restTemplateClient, webclient, feignClient);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }
}