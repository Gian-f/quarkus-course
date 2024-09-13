package com.br.coffeeandit.config;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


@OpenAPIDefinition(
        info = @Info(
                title = "Quarkus 3.0",
                version = "1.0.0",
                contact = @Contact(
                        name = "Gian Felipe da Silva",
                        url = "https://www.linkedin.com/in/gian-felipe/",
                        email = "gianfelipe87@gmail.com"
                ),
                license = @License(name = "MIT License")
        )
)
public class OpenAPIConfig extends Application {

}
