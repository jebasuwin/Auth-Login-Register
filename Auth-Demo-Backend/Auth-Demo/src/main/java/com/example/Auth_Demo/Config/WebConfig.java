package com.example.Auth_Demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Apply to all endpoints
                        .allowedOrigins("http://localhost:5173") // Allow frontend origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow all HTTP methods
                        .allowedHeaders("*") // Allow all headers
                        .exposedHeaders("Authorization") // Expose Authorization header if needed
                        .allowCredentials(true); // Allow cookies or credentials if required
            }
        };
    }
}
