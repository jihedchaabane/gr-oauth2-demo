package com.chj.gr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class ConsumerWebClientPublicController {

	private WebClient webClient;
	
    public ConsumerWebClientPublicController(WebClient webClient) {
		this.webClient = webClient;
	}



	@GetMapping("/call-ms1-public")
    public String callMs1Public() {
    	
        String url = "http://GR-API-GATEWAY/ms1/gr-ms1-resource/public/ms2";
        String responseJson = this.webClient
        		.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        
        return "GR-RESOURCE-CONSUMER-WEBCLIENT ==> GR-API-GATEWAY ==> " + responseJson;
    }
    


    @GetMapping("/call-ms2-public")
    public String callMs2Public() {
    	
        String url = "http://GR-API-GATEWAY/ms2/gr-ms2-resource/public/get";
        String responseJson = this.webClient
        		.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        
        return "GR-RESOURCE-CONSUMER-WEBCLIENT ==> GR-API-GATEWAY ==> " + responseJson;
    }
}