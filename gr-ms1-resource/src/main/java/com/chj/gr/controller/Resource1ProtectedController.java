package com.chj.gr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/gr-ms1-resource/protected")
public class Resource1ProtectedController {

	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;

	@Operation(summary = "Call MS2 protected endpoint", security = @SecurityRequirement(name = "oauth2"))
	@GetMapping("/ms2")
	@PreAuthorize("hasAuthority('SCOPE_ms1.read')")
	public String callMs2(@Parameter(hidden = true) @RegisteredOAuth2AuthorizedClient("ms1") OAuth2AuthorizedClient authorizedClient) {
		
		HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authorizedClient.getAccessToken().getTokenValue());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
        		"http://GR-MS2-RESOURCE/gr-ms2-resource/protected/get",
                HttpMethod.GET,
                entity,
                String.class
        );
        return "GR-MS1-RESOURCE [SECURED:ms1.read] ===> " + response.getBody();
	}
}