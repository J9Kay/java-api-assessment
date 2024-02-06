package com.cbfacademy.apiassessment.core;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact (
                        name = "Janine",
                        email = "janine.kayode@gmail.com"
                ) ,
                description = "Documentation for STOCK API",
                title = "StockAPI",
                version = "1.0",
                termsOfService = "Terms of service"

        )
)
public class OpenApiConfig {
}
