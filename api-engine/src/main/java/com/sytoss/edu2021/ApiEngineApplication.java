package com.sytoss.edu2021;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients
@SpringBootApplication
public class ApiEngineApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiEngineApplication.class, args);
    }
}
