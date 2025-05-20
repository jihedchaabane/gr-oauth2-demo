package com.chj.gr.controller;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class ConsumerWebClientProtectedController {

	@Autowired
	private WebClient webClient;

    @GetMapping("/call-ms1-protected")
    public String callMs1(@RegisteredOAuth2AuthorizedClient("consumer-webclient") OAuth2AuthorizedClient authorizedClient) {
    	String responseJson =  this.webClient
                .get()
                .uri("http://GR-API-GATEWAY/ms1/gr-ms1-resource/protected/ms2")
                .attributes(clientRegistrationId("consumer-webclient"))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    	/**
    	 * OR : WORKS FINE TOO.
    	 */
//    	String responseJson =  this.webClient
//                .get()
//                .uri("http://GR-API-GATEWAY/ms1/gr-ms1-resource/protected/ms2")
//                .header("Authorization", "Bearer " + authorizedClient.getAccessToken().getTokenValue())
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
    	
        return "GR-RESOURCE-CONSUMER-WEBCLIENT ==> GR-API-GATEWAY ==> " + responseJson;
    }
    
    @GetMapping("/call-ms2-protected")
    public String callMs2(@RegisteredOAuth2AuthorizedClient("consumer-webclient") OAuth2AuthorizedClient authorizedClient) {
    	String responseJson =  this.webClient
                .get()
                .uri("http://GR-API-GATEWAY/ms2/gr-ms2-resource/protected/get")
                .attributes(clientRegistrationId("consumer-webclient"))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    	/**
    	 * OR : WORKS FINE TOO.
    	 */
//    	String responseJson =  this.webClient
//    			.get()
//                .uri("http://GR-API-GATEWAY/ms2/gr-ms2-resource/protected/get")
//                .header("Authorization", "Bearer " + authorizedClient.getAccessToken().getTokenValue())
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
    	
        return "GR-RESOURCE-CONSUMER-WEBCLIENT ==> GR-API-GATEWAY ==> " + responseJson;
    }
}