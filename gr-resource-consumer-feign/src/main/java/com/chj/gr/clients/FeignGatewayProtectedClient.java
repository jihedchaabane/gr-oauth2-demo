package com.chj.gr.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * WORKS
https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/#netflix-feign-starter
config:
  feign:
    gateway: GR-API-GATEWAY
@FeignClient(name = "${config.feign.gateway}", contextId = "gr-resource-consumer-feign-protected")
*/

/** @TODO SPEL NOT WORKING */
//@FeignClient(name = "#{${params.gatewayOauth2.uri}.replace('http://', '')}", contextId = "gr-resource-consumer-feign-protected")

@FeignClient(name = "GR-API-GATEWAY", contextId = "gr-resource-consumer-feign-protected")
public interface FeignGatewayProtectedClient {
	/**
	 * PROTECTED endpoints.
	 */
    @GetMapping("/ms1/gr-ms1-resource/protected/ms2")
    String callMs1Protected();
    
    @GetMapping("/ms2/gr-ms2-resource/protected/get")
    String callMs2Protected();
    
    @GetMapping("/ms3/gr-ms3-resource/protected/get")
    String callMs3Protected();

}