package com.chj.gr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/gr-ms1-resource/public")
public class Resource1PublicController {

	@Autowired
	private RestTemplate restTemplate;

	@Operation(summary = "Call MS2 public endpoint")
	@GetMapping("/ms2")
	public String callMs2() {
		
		String url = "http://GR-MS2-RESOURCE/gr-ms2-resource/public/get";
		String response = restTemplate.getForObject(url, String.class);
		
        return "GR-MS1-RESOURCE [PUBLIC] ===> " + response;
	}
}