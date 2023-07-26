package com.upgrade.campsite.reservations.application.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private Info serviceInfo() {
        return new Info()
                .title("Campsite Reservations API")
                .description("Campsite Reservations Service")
                .version("v0.0.1");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(serviceInfo());
    }
}
