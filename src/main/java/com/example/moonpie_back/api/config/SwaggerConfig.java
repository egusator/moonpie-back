package com.example.moonpie_back.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        ArrayList<String> errorCodes = new ArrayList<>();
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("ApiKey"))
                .components(new Components()
                        .addSecuritySchemes("ApiKey", new SecurityScheme()
                                .in(SecurityScheme.In.HEADER)
                                .description("Please enter a valid token")
                                .name("Authorization")
                                .type(SecurityScheme.Type.APIKEY)
                        )
                ).info(new Info()
                        .title("Moonpie API")
                        .version("1.0"));
    }

}
