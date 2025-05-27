package com.chj.gr.controller.ms1;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class ConsumerWebClientMS1Controller {

	@Autowired
	private WebClient webClient;

	@Operation(summary = "Call GR-MS1-RESOURCE to access GR-MS2-RESOURCE", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/call-ms1-protected")
    public String callMs1(@Parameter(hidden = true) @RegisteredOAuth2AuthorizedClient("consumer-webclient") OAuth2AuthorizedClient authorizedClient) {
    	String responseJson =  this.webClient
                .get()
                .uri("http://SPRINGBOOT-CONF-GATEWAY-API-OAUTH2/ms1/gr-ms1-resource/protected/ms2")
                .attributes(clientRegistrationId("consumer-webclient"))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    	/**
    	 * OR : WORKS FINE TOO.
    	 */
//    	String responseJson =  this.webClient
//                .get()
//                .uri("http://SPRINGBOOT-CONF-GATEWAY-API-OAUTH2/ms1/gr-ms1-resource/protected/ms2")
//                .header("Authorization", "Bearer " + authorizedClient.getAccessToken().getTokenValue())
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
    	
        return "GR-RESOURCE-CONSUMER-WEBCLIENT ==> SPRINGBOOT-CONF-GATEWAY-API-OAUTH2 ==> " + responseJson;
    }
	
	@Operation(summary = "Call GR-MS1-RESOURCE public endpoint")
	@GetMapping("/call-ms1-public")
    public String callMs1Public() {
    	
        String url = "http://SPRINGBOOT-CONF-GATEWAY-API-OAUTH2/ms1/gr-ms1-resource/public/ms2";
        String responseJson = this.webClient
        		.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        
        return "GR-RESOURCE-CONSUMER-WEBCLIENT ==> SPRINGBOOT-CONF-GATEWAY-API-OAUTH2 ==> " + responseJson;
	}
}