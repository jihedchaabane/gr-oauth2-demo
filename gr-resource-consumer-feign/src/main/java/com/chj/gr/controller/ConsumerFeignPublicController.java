package com.chj.gr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chj.gr.clients.FeignGatewayPublicClient;

@RestController
public class ConsumerFeignPublicController {

	private FeignGatewayPublicClient feignGatewayPublicClient;
	
	public ConsumerFeignPublicController(FeignGatewayPublicClient feignGatewayPublicClient) {
		this.feignGatewayPublicClient = feignGatewayPublicClient;
	}


	@GetMapping("/call-ms1-public")
    public String callMs1Public() {
    	
		String responseJson = feignGatewayPublicClient.callMs1Public();
        
        return "GR-RESOURCE-CONSUMER-FEIGN ==> GR-API-GATEWAY ==> " + responseJson;
    }

    @GetMapping("/call-ms2-public")
    public String callMs2Public() {
    	
    	String responseJson = feignGatewayPublicClient.callMs2Public();
        
        return "GR-RESOURCE-CONSUMER-FEIGN ==> GR-API-GATEWAY ==> " + responseJson;
    }
}