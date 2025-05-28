package com.chj.gr.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.chj.gr.config.properties.CallerDestinationProperties;
import com.chj.gr.config.properties.CallerDestinationProperties.DestinationClient;
import com.chj.gr.enums.EnumResourceServer;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/gr-ms1-resource/public")
public class Resource1PublicController {

	private RestTemplate restTemplate;
	private CallerDestinationProperties callerDestinationProperties;
	
	public Resource1PublicController(@Qualifier("restTemplate") RestTemplate restTemplate,
			CallerDestinationProperties callerDestinationProperties) {
		this.restTemplate = restTemplate;
		this.callerDestinationProperties = callerDestinationProperties;
	}

	@Operation(summary = "Call MS2 public endpoint")
	@GetMapping("/ms2")
	public String callMs2() {
		
		DestinationClient destinationClient = callerDestinationProperties.getDestinationClient(
    			EnumResourceServer.STS_GR_MS2_RESOURCE_REGISTRATION.getKey());
		
		String url = destinationClient.getResourceUri().concat("/gr-ms2-resource/public/get");
		String response = restTemplate.getForObject(url, String.class);
		
        return "GR-MS1-RESOURCE [PUBLIC] ===> " + response;
	}
}