package com.perfumeria.autenticacionservice.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Perfumería - Servicio de Autenticación")
                        .version("1.0")
                        .description("Registro, login y generación de tokens JWT para la Perfumería."))
                .servers(List.of(
                        new Server().url("http://localhost:9090").description("API Gateway")
                ));
    }
}
