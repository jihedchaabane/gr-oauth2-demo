package com.chj.gr.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/gr-ms2-resource/protected")
public class Resource2ProtectedController {

	@Operation(summary = "MS2 protected endpoint", security = @SecurityRequirement(name = "oauth2"))
    @GetMapping("/get")
    @PreAuthorize("hasAuthority('SCOPE_ms2.read')")
    public String get() {
    	
        return "GR-MS2-RESOURCE [SECURED:ms2.read] Hello from /protected/get endpoint";
    }
}