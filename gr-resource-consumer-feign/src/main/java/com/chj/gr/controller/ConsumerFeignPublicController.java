package com.chj.gr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chj.gr.clients.FeignGatewayClient;

@RestController
public class ConsumerFeignPublicController {

	private FeignGatewayClient feignGatewayClient;
	
	public ConsumerFeignPublicController(FeignGatewayClient feignGatewayClient) {
		this.feignGatewayClient = feignGatewayClient;
	}



	@GetMapping("/call-ms1-public")
    public String callMs1Public() {
    	
		String responseJson = feignGatewayClient.callMs1Public();
        
        return "GR-RESOURCE-CONSUMER-FEIGN ==> GR-API-GATEWAY ==> " + responseJson;
    }
    


    @GetMapping("/call-ms2-public")
    public String callMs2Public() {
    	
    	String responseJson = feignGatewayClient.callMs2Public();
        
        return "GR-RESOURCE-CONSUMER-FEIGN ==> GR-API-GATEWAY ==> " + responseJson;
    }
}