package com.chj.gr.controller;

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

@RestController
public class ConsumerProtectedController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/call-ms1-protected")
    public String callMs1(@RegisteredOAuth2AuthorizedClient("consumer-resttemplate") OAuth2AuthorizedClient authorizedClient) {
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authorizedClient.getAccessToken().getTokenValue());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
        		"http://GR-API-GATEWAY/ms1/gr-ms1-resource/protected/ms2",
                HttpMethod.GET,
                entity,
                String.class
        );
        return "GR-RESOURCE-CONSUMER ==> GR-API-GATEWAY ==> " + response.getBody();
    }
    
    @GetMapping("/call-ms2-protected")
    public String callMs2(@RegisteredOAuth2AuthorizedClient("consumer-resttemplate") OAuth2AuthorizedClient authorizedClient) {
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authorizedClient.getAccessToken().getTokenValue());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
        		"http://GR-API-GATEWAY/ms2/gr-ms2-resource/protected/get",
                HttpMethod.GET,
                entity,
                String.class
        );
        return "GR-RESOURCE-CONSUMER ==> GR-API-GATEWAY ==> " + response.getBody();
    }
}