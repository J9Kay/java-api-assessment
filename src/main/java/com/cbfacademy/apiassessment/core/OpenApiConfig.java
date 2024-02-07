package com.cbfacademy.apiassessment.core;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;


/**
 * Configures the OpenAPI documentation for the STOCK API.
 *
 * The {@code OpenApiConfig} class is used to define the OpenAPI documentation for the STOCK API.
 * It contains the necessary annotations to specify the information about the API, such as the
 * contact information, description, title, version, and terms of service.
 *
 * @see OpenAPIDefinition
 * @see Info
 * @see Contact
 */
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
