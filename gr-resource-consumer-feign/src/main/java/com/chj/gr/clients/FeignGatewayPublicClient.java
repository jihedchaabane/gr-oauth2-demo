package com.chj.gr.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "GR-API-GATEWAY", contextId = "gr-resource-consumer-feign-public")
public interface FeignGatewayPublicClient {

	/**
	 * PUBLIC endpoints.
	 */
    @GetMapping("/ms1/gr-ms1-resource/public/ms2")
    String callMs1Public();
    
    @GetMapping("/ms2/gr-ms2-resource/public/get")
    String callMs2Public();
}