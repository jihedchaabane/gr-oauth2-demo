package com.chj.gr.controller.ms1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class ConsumerRestTemplateMS1PublicController {

    @Autowired
    private RestTemplate restTemplate;

    @Operation(summary = "Call GR-MS1-RESOURCE public endpoint")
    @GetMapping("/call-ms1-public")
    public String callMs1Public() {
    	
        String url = "http://GR-API-GATEWAY/ms1/gr-ms1-resource/public/ms2";
        return "GR-RESOURCE-CONSUMER-RESTTEMPLATE ==> GR-API-GATEWAY ==> " + restTemplate.getForObject(url, String.class);
    }
    
}