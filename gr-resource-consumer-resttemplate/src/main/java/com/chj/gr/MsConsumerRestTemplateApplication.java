package com.chj.gr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsConsumerRestTemplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsConsumerRestTemplateApplication.class, args);
    }
}