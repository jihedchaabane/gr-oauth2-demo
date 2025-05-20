package com.chj.gr.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "GR-API-GATEWAY", contextId = "gr-resource-consumer-feign-protected")
public interface FeignGatewayProtectedClient {
	/**
	 * PROTECTED endpoints.
	 */
    @GetMapping("/ms1/gr-ms1-resource/protected/ms2")
    String callMs1Protected();
    
    @GetMapping("/ms2/gr-ms2-resource/protected/get")
    String callMs2Protected();

}