package com.chj.gr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
