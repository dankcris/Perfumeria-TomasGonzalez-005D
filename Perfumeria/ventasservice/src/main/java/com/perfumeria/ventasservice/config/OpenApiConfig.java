package com.perfumeria.ventasservice.config;
import io.swagger.v3.oas.models.*; import io.swagger.v3.oas.models.info.Info; import io.swagger.v3.oas.models.security.*; import org.springframework.context.annotation.*;
@Configuration public class OpenApiConfig {
    @Bean public OpenAPI customOpenAPI() { return new OpenAPI().info(new Info().title("Ventas Service API").version("1.0").description("Microservicio de ventas - Perfumería Tomas González 005D")).addSecurityItem(new SecurityRequirement().addList("Bearer Authentication")).components(new Components().addSecuritySchemes("Bearer Authentication",new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))); }
}
