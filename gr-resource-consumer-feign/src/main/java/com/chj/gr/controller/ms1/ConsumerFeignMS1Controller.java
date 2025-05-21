package com.chj.gr.controller.ms1;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chj.gr.clients.FeignGatewayProtectedClient;
import com.chj.gr.clients.FeignGatewayPublicClient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class ConsumerFeignMS1Controller {

	private FeignGatewayProtectedClient feignGatewayProtectedClient;
	private FeignGatewayPublicClient feignGatewayPublicClient;
	/** 
	@Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
	 */
	public ConsumerFeignMS1Controller(
			FeignGatewayProtectedClient feignGatewayProtectedClient,
			FeignGatewayPublicClient feignGatewayPublicClient) {
		this.feignGatewayProtectedClient = feignGatewayProtectedClient;
		this.feignGatewayPublicClient = feignGatewayPublicClient;
	}

	@Operation(summary = "Call GR-MS1-RESOURCE to access GR-MS2-RESOURCE", security = @SecurityRequirement(name = "oauth2"))
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
	
	@Operation(summary = "Call GR-MS1-RESOURCE public endpoint")
	@GetMapping("/call-ms1-public")
    public String callMs1Public() {
    	
		String responseJson = feignGatewayPublicClient.callMs1Public();
        
        return "GR-RESOURCE-CONSUMER-FEIGN ==> GR-API-GATEWAY ==> " + responseJson;
    }
}