package com.spacecraft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpacecraftApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpacecraftApplication.class, args);
    }
}
