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

import com.chj.gr.config.properties.ServiceParamsProperties;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class AuthServerSecurityConfig {
	private static final Logger logger = LoggerFactory.getLogger(AuthServerSecurityConfig.class);
	
	private CorsConfigurationSource corsConfigurationSource;
	private ServiceParamsProperties serviceParamsProperties;
	
	public AuthServerSecurityConfig(CorsConfigurationSource corsConfigurationSource, ServiceParamsProperties serviceParamsProperties) {
		this.corsConfigurationSource = corsConfigurationSource;
		this.serviceParamsProperties = serviceParamsProperties;
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
        		.issuer(serviceParamsProperties.getOauth2().getIssuerUri())
        		.tokenEndpoint("/oauth2/token")
                .build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient ms1Client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gr-ms1-resource")
                .clientSecret("{noop}ms1-resource")
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
        RegisteredClient ms2Client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gr-ms2-resource")
                .clientSecret("{noop}ms2-resource")
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
        RegisteredClient ms3Client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gr-ms3-resource")
                .clientSecret("{noop}ms3-resource")
                .scope("ms3.read")
                .scope("actuator.read")
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
        		ms3Client.getClientId(),
        		ms3Client.getScopes(),
        		ms3Client.getAuthorizationGrantTypes());
        RegisteredClient restTemplateClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gr-resource-consumer-resttemplate")
                .clientSecret("{noop}consumer-resttemplate")
                .scope("ms1.read")
                .scope("ms2.read")
                .scope("ms3.read")
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
                .scope("ms3.read")
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
                .scope("ms3.read")
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
        /**
         * gr-oauth2-swagger-demo
         */
        /** gr-oauth2-swagger-ms1 */
        RegisteredClient grOauth2SwaggerMs1 = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gr-oauth2-swagger-ms1")
                .clientSecret("{noop}swagger-ms1")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("read")
                .scope("write")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                .tokenSettings(
                		/**
                		 * Expiration de 1 jour.
                		 */
                		TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(1)).build()
                )
                .build();
        logger.info("Client créé : clientId={}, scopes={}, grantTypes={}",
        		grOauth2SwaggerMs1.getClientId(),
        		grOauth2SwaggerMs1.getScopes(),
        		grOauth2SwaggerMs1.getAuthorizationGrantTypes());
        /** gr-oauth2-swagger-ms2 */
        RegisteredClient grOauth2SwaggerMs2 = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("gr-oauth2-swagger-ms2")
                .clientSecret("{noop}swagger-ms2")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("update")
                .scope("remove")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                .tokenSettings(
                		/**
                		 * Expiration de 1 jour.
                		 */
                		TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(1)).build()
                )
                .build();
        logger.info("Client créé : clientId={}, scopes={}, grantTypes={}",
        		grOauth2SwaggerMs2.getClientId(),
        		grOauth2SwaggerMs2.getScopes(),
        		grOauth2SwaggerMs2.getAuthorizationGrantTypes());
        /**
         * sts-oauth2-client-credentials-server-to-server-demo
         */
        /** springboot-oauth2-client1 */
        RegisteredClient client1 = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("client1")
                .clientSecret("{noop}secret1")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("client1.read")
                .scope("client1.write")
                .scope("client2.read")
                .scope("client2.write")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                .tokenSettings(
                		/**
                		 * Expiration de 1 jour.
                		 */
                		TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(1)).build()
                )
                .build();
        logger.info("Client créé : clientId={}, scopes={}, grantTypes={}",
        		client1.getClientId(),
        		client1.getScopes(),
        		client1.getAuthorizationGrantTypes());
       /** springboot-oauth2-client2 */
       RegisteredClient client2 = RegisteredClient.withId(UUID.randomUUID().toString())
               .clientId("client2")
               .clientSecret("{noop}secret2")
               .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
               .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
               .scope("client2.read")
               .scope("client2.write")
               .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
               .tokenSettings(
               		/**
               		 * Expiration de 1 jour.
               		 */
               		TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(1)).build()
               )
               .build();
       logger.info("Client créé : clientId={}, scopes={}, grantTypes={}",
       		client2.getClientId(),
       		client2.getScopes(),
       		client2.getAuthorizationGrantTypes());
       /** sts-spring-boot-resource-server */
       RegisteredClient client3 = RegisteredClient.withId(UUID.randomUUID().toString())
               .clientId("products-client")
               .clientSecret("{noop}secret")
               .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
               .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
               .scope("products.read")
               .scope("products.write")
               .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
               .tokenSettings(
               		/**
               		 * Expiration de 1 jour.
               		 */
               		TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(1)).build()
               )
               .build();
       logger.info("Client créé : clientId={}, scopes={}, grantTypes={}",
       		client3.getClientId(),
       		client3.getScopes(),
       		client3.getAuthorizationGrantTypes());
       
       
        return new InMemoryRegisteredClientRepository(
        		ms1Client, 
        		ms2Client, 
        		ms3Client,
        		restTemplateClient, 
        		webclient, 
        		feignClient,
        		/**
        		 * gr-oauth2-swagger-demo.
        		 */
        		grOauth2SwaggerMs1,
        		grOauth2SwaggerMs2,
        		/**
        		 * 
        		 */
        		client1,
        		client2,
        		client3
        );
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    private static RSAKey generateRsa() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }
}