package com.perfumeria.promocionesservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Promociones Service API").version("1.0")
                .description("Microservicio para gestión de promociones - Perfumería Tomas González 005D"));
    }
}
