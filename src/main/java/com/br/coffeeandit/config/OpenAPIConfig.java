package com.br.coffeeandit.config;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


@OpenAPIDefinition(
        info = @Info(
                title = "Sistema de exemplo de transações, curso Quarkus 3.0 da CoffeeandIT",
                version = "1.0.0",
                contact = @Contact(
                        name = "Gian Felipe da Silva",
                        url = "https://www.linkedin.com/in/gian-felipe/",
                        email = "gianfelipe87@gmail.com"
                ),
                license = @License(name = "MIT License")
        ),
        tags = {
                @Tag(name = "v1/pix", description = "Grupo de API's para manipulação de transações PIX"),
                @Tag(name = "v1/chaves", description = "Grupo de API's para manipulação de chaves PIX")
        },
        servers = {
                @Server(url = "http://localhost:8090")
        },
        security = {
                @SecurityRequirement(name = "jwt", scopes = {"coffeeandit"})
        },
        components = @Components(
                securitySchemes = {
                        @SecurityScheme(
                                securitySchemeName = "jwt",
                                type = SecuritySchemeType.HTTP
                        )
                }
        )
)
public class OpenAPIConfig extends Application {

}
