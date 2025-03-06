package com.example.LendEase.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow specific origin - your React frontend
        config.addAllowedOrigin("http://localhost:3000");

        // Allow all HTTP methods
        config.addAllowedMethod("*");

        // Allow all headers
        config.addAllowedHeader("*");

        // Allow credentials such as cookies
        config.setAllowCredentials(true);

        // Maximum age of pre-flight requests cache (in seconds)
        config.setMaxAge(3600L);

        // Apply this configuration to all endpoints
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}