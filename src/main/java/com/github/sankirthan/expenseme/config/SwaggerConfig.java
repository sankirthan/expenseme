package com.github.sankirthan.expenseme.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("ExpenseMe API")
                        .description("ExpenseMe REST API endpoints")
                        .version("v1.0"));

    }
}
