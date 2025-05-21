package com.chj.gr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/gr-ms3-resource/public")
public class Resource3PublicController {

	@Operation(summary = "GR-MS3-RESOURCE public endpoint")
    @GetMapping("/get")
    public String publicEndpoint() {
    	
        return "GR-MS3-RESOURCE [PUBLIC] Hello from /public/get endpoint";
    }
}