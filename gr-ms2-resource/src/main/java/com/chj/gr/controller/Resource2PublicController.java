package com.chj.gr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gr-ms2-resource/public")
public class Resource2PublicController {

    @GetMapping("/get")
    public String publicEndpoint() {
    	
        return "GR-MS2-RESOURCE [PUBLIC] Hello from /public/get endpoint";
    }
}