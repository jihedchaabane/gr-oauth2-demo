package com.chj.gr.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthServerController {

    @GetMapping("/hello")
    public String hello(@RequestHeader("Authorization") String authHeader) {
        return "Hello, your token is: " + authHeader;
    }
    
    @RequestMapping("/info")
	public Principal user(Principal user) {
		
		System.out.println("java.security.Principal user");
		return user;
	}
}
