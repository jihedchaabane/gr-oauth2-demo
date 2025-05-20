package com.chj.gr.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chj.gr.clients.FeignGatewayClient;

@RestController
public class ConsumerFeignProtectedController {

	private FeignGatewayClient feignGatewayClient;
	@Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
	public ConsumerFeignProtectedController(FeignGatewayClient feignGatewayClient) {
		this.feignGatewayClient = feignGatewayClient;
	}

	
	@GetMapping("/call-ms1-protected")
	public String callMs1Protected(@RegisteredOAuth2AuthorizedClient("feign") OAuth2AuthorizedClient authorizedClient) {
		// Create an Authentication object with the client ID as the principal
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "feign",
                null,
                Collections.emptyList()
        );
        // Save the authorized client with the Authentication object
        authorizedClientService.saveAuthorizedClient(authorizedClient, authentication);
        
        
        
        
		String responseJson = feignGatewayClient.callMs1Protected();
		
		return "GR-RESOURCE-CONSUMER-FEIGN ==> GR-API-GATEWAY ==> " + responseJson;
	}

	@GetMapping("/call-ms2-protected")
	public String callMs2Protected(@RegisteredOAuth2AuthorizedClient("feign") OAuth2AuthorizedClient authorizedClient) {
		// Create an Authentication object with the client ID as the principal
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "feign",
                null,
                Collections.emptyList()
        );
        // Save the authorized client with the Authentication object
        authorizedClientService.saveAuthorizedClient(authorizedClient, authentication);
        
        
        
        
		String responseJson = feignGatewayClient.callMs2Protected();
		
		return "GR-RESOURCE-CONSUMER-FEIGN ==> GR-API-GATEWAY ==> " + responseJson;
	}
}