package com.chj.gr.controller;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chj.gr.clients.FeignGatewayProtectedClient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class ConsumerFeignProtectedController {

	private FeignGatewayProtectedClient feignGatewayProtectedClient;
	/** 
	@Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
	 */
	public ConsumerFeignProtectedController(FeignGatewayProtectedClient feignGatewayProtectedClient) {
		this.feignGatewayProtectedClient = feignGatewayProtectedClient;
	}

	@Operation(summary = "Call MS1 to access MS2", security = @SecurityRequirement(name = "oauth2"))
	@GetMapping("/call-ms1-protected")
	public String callMs1Protected(@Parameter(hidden = true) @RegisteredOAuth2AuthorizedClient("feign") OAuth2AuthorizedClient authorizedClient) {
		/** 
		 ==> Pour que ça soit exécuté une seule fois: Voir : FeignStartupConfig.java et FeignOAuth2Config.java.
		 
		// Create an Authentication object with the client ID as the principal
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "feign",
                null,
                Collections.emptyList()
        );
        // Save the authorized client with the Authentication object
        authorizedClientService.saveAuthorizedClient(authorizedClient, authentication);
        */
        
		String responseJson = feignGatewayProtectedClient.callMs1Protected();
		
		return "GR-RESOURCE-CONSUMER-FEIGN ==> GR-API-GATEWAY ==> " + responseJson;
	}

	@Operation(summary = "Call MS2", security = @SecurityRequirement(name = "oauth2"))
	@GetMapping("/call-ms2-protected")
	public String callMs2Protected(@Parameter(hidden = true) @RegisteredOAuth2AuthorizedClient("feign") OAuth2AuthorizedClient authorizedClient) {
	  /**
		 ==> Pour que ça soit exécuté une seule fois: Voir : FeignStartupConfig.java et FeignOAuth2Config.java.
		 
		// Create an Authentication object with the client ID as the principal
       Authentication authentication = new UsernamePasswordAuthenticationToken(
               "feign",
               null,
               Collections.emptyList()
       );
       // Save the authorized client with the Authentication object
       authorizedClientService.saveAuthorizedClient(authorizedClient, authentication);
       */
        
		String responseJson = feignGatewayProtectedClient.callMs2Protected();
		
		return "GR-RESOURCE-CONSUMER-FEIGN ==> GR-API-GATEWAY ==> " + responseJson;
	}
}