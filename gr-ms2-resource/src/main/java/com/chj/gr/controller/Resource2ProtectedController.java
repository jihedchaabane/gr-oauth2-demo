package com.chj.gr.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gr-ms2-resource/protected")
public class Resource2ProtectedController {

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('SCOPE_ms2.read')")
    public String get() {
    	
        return "GR-MS2-RESOURCE [SECURED:ms2.read] Hello from /protected/get endpoint";
    }
}