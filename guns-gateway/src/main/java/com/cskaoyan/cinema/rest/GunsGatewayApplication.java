package com.cskaoyan.cinema.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.cskaoyan.cinema"})
public class GunsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GunsGatewayApplication.class, args);
    }
}
