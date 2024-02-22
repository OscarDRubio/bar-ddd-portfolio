package com.spacecraft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableCaching
@SpringBootApplication
@EnableJpaRepositories("com.spacecraft.repository")
@ComponentScan(basePackages = {"com.spacecraft.aspect", "com.spacecraft.repository", "com.spacecraft.controller",
        "com.spacecraft.model", "com.spacecraft.exception"})
public class SpacecraftApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpacecraftApplication.class, args);
    }
}
