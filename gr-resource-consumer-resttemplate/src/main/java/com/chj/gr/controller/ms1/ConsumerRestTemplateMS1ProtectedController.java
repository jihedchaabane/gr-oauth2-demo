package com.chj.gr.controller.ms1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class ConsumerRestTemplateMS1ProtectedController {

    @Autowired
    private RestTemplate restTemplate;

    @Operation(summary = "Call GR-MS1-RESOURCE to access GR-MS2-RESOURCE", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/call-ms1-protected")
    public String callMs1(@Parameter(hidden = true) @RegisteredOAuth2AuthorizedClient("consumer-resttemplate") OAuth2AuthorizedClient authorizedClient) {
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authorizedClient.getAccessToken().getTokenValue());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
        		"http://GR-API-GATEWAY/ms1/gr-ms1-resource/protected/ms2",
                HttpMethod.GET,
                entity,
                String.class
        );
        return "GR-RESOURCE-CONSUMER-RESTTEMPLATE ==> GR-API-GATEWAY ==> " + response.getBody();
    }
}