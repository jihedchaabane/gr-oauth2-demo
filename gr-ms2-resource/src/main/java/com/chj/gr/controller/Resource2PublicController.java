package com.chj.gr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/gr-ms2-resource/public")
public class Resource2PublicController {

	@Operation(summary = "MS2 public endpoint")
    @GetMapping("/get")
    public String publicEndpoint() {
    	
        return "GR-MS2-RESOURCE [PUBLIC] Hello from /public/get endpoint";
    }
}