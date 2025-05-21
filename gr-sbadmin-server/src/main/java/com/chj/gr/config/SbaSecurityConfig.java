package com.chj.gr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//@Configuration
//@EnableWebSecurity
//public class SbaSecurityConfig {
	
//	@Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http
//		.authorizeRequests()
//		.antMatchers("/actuator/**").permitAll()
//		.anyRequest().authenticated()
//		.and()
//			.formLogin()
//		.and()
//			.httpBasic()
//		.and()
//			.csrf().disable();
//
//        return http.build();
//    }
//}
	

@Configuration
@EnableWebSecurity
public class SbaSecurityConfig extends WebSecurityConfigurerAdapter {

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	            .authorizeRequests()
	                .antMatchers("/actuator/**").permitAll()
	                .antMatchers("/assets/**", "/login").permitAll()
	                .anyRequest().authenticated()
	            .and()
	            .formLogin()
	                .loginPage("/login")
	                .defaultSuccessUrl("/applications")
	                .permitAll()
	            .and()
	            .httpBasic()
	            .and()
	            .csrf().disable()
	            .logout()
	                .logoutUrl("/logout")
	                .logoutSuccessUrl("/login?logout");
	    }

	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.inMemoryAuthentication()
	            .withUser("admin")
	            .password("{noop}admin")
	            .roles("ADMIN");
	    }

	    @Override
	    protected UserDetailsService userDetailsService() {
	        return new InMemoryUserDetailsManager(
	            User.withUsername("admin")
	                .password("{noop}admin")
	                .roles("ADMIN")
	                .build()
	        );
	    }
}
