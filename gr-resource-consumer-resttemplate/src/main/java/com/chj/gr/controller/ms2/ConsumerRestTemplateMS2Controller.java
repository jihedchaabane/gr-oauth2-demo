package com.chj.gr.controller.ms2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class ConsumerRestTemplateMS2Controller {

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Operation(summary = "Call GR-MS2-RESOURCE", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/call-ms2-protected")
    public String callMs2(@Parameter(hidden = true) @RegisteredOAuth2AuthorizedClient("consumer-resttemplate") OAuth2AuthorizedClient authorizedClient) {
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authorizedClient.getAccessToken().getTokenValue());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
        		"http://GR-API-GATEWAY/ms2/gr-ms2-resource/protected/get",
                HttpMethod.GET,
                entity,
                String.class
        );
        return "GR-RESOURCE-CONSUMER-RESTTEMPLATE ==> GR-API-GATEWAY ==> " + response.getBody();
    }

    @Operation(summary = "Call GR-MS2-RESOURCE public endpoint")
    @GetMapping("/call-ms2-public")
    public String callMs2Public() {
    	
        String url = "http://GR-API-GATEWAY/ms2/gr-ms2-resource/public/get";
        return "GR-RESOURCE-CONSUMER-RESTTEMPLATE ==> GR-API-GATEWAY ==> " + restTemplate.getForObject(url, String.class);
    }
}