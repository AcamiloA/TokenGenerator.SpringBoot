package com.andresalarcon.tokengenerator.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customAPI(){
        return new OpenAPI()
                    .info(new Info()
                        .title("Generator Token API")
                        .version("1.0")
                        .description("API diseñada para la clase de programación IV TS7B 2025 Escuela tecnológica Instituto Técnico Central"));
    }    
}
