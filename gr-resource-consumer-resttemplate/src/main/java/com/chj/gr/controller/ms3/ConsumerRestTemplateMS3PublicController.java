package com.chj.gr.controller.ms3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class ConsumerRestTemplateMS3PublicController {

    @Autowired
    private RestTemplate restTemplate;

    @Operation(summary = "Call GR-MS3-RESOURCE public endpoint")
    @GetMapping("/call-ms3-public")
    public String callMs3Public() {
    	
        String url = "http://GR-API-GATEWAY/ms3/gr-ms3-resource/public/get";
        return "GR-RESOURCE-CONSUMER-RESTTEMPLATE ==> GR-API-GATEWAY ==> " + restTemplate.getForObject(url, String.class);
    }
    
}