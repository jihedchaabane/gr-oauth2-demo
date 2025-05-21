package com.chj.gr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;

import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.LoggingNotifier;
import de.codecentric.boot.admin.server.notify.Notifier;
/**
 * Configures a LoggingNotifier to log instance status changes (e.g., UP, DOWN).
 * 
 * SBA automatically uses Eureka discovery if spring-cloud-starter-netflix-eureka-client 
 * 		is present and spring.cloud.discovery.enabled is true.
 */
@Configuration
public class SbaEurekaConfig {

	@Bean
	public Notifier loggingNotifier(InstanceRepository repository) {
		return new LoggingNotifier(repository);
	}
	
	/**
	 * 
	 */
//	@Bean
//	public MailNotifier mailNotifier(InstanceRepository repository) {
//	    MailNotifier notifier = new MailNotifier(repository);
//	    notifier.setTo("admin@example.com");
//	    notifier.setFrom("sba@example.com");
//	    return notifier;
//	}
	


    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {
        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build();
        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
                new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        return authorizedClientManager;
    }
}
