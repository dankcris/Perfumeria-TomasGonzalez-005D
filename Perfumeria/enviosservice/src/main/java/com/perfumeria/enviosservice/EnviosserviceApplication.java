package com.perfumeria.enviosservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EnviosserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnviosserviceApplication.class, args);
    }
}
