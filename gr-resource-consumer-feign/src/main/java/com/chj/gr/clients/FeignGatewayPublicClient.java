package com.chj.gr.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * WORKS
https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/#netflix-feign-starter
config:
  feign:
    gateway: SPRINGBOOT-CONF-GATEWAY-API-OAUTH2
@FeignClient(name = "${config.feign.gateway}", contextId = "gr-resource-consumer-feign-public")
*/

/** @TODO SPEL NOT WORKING */
//@FeignClient(name = "#{${params.gatewayOauth2.uri}.replace('http://', '')}", contextId = "gr-resource-consumer-feign-public")

@FeignClient(name = "SPRINGBOOT-CONF-GATEWAY-API-OAUTH2", contextId = "gr-resource-consumer-feign-public")
public interface FeignGatewayPublicClient {
	/**
	 * PUBLIC endpoints.
	 */
    @GetMapping("/ms1/gr-ms1-resource/public/ms2")
    String callMs1Public();
    
    @GetMapping("/ms2/gr-ms2-resource/public/get")
    String callMs2Public();
    
    @GetMapping("/ms3/gr-ms3-resource/public/get")
    String callMs3Public();
}