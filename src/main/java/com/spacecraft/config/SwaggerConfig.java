package com.spacecraft.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Value("${spacecraft.swagger.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI mySpacecraft() {

        Contact contact = new Contact();
        contact.setEmail("oscardrm89@gmail.com");
        contact.setName("Oscar Rubio");
        contact.setUrl("https://www.linkedin.com/in/%C3%B3scar-david-rubio-molina/");

        Info info = new Info()
                .title("Spacecraft API")
                .version("1.0")
                .contact(contact)
                .description("Basic CRUD for spaceship repository.");

        return new OpenAPI().info(info);
    }
}
