package com.chj.gr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MsCommonConsumerSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
    		.antMatchers("/call-ms1-public").permitAll()
    		.antMatchers("/call-ms2-public").permitAll()
    		.antMatchers("/call-ms3-public").permitAll()
            
            .antMatchers("/actuator/**").permitAll()
            .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll();

        return http.build();
    }
}