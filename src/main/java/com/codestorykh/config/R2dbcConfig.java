/*
package com.codestorykh.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Java configuration for R2DBC
@Configuration
public class R2dbcConfig {

    @Value("${spring.datasource.r2dbc.url}")
    private String url;

    @Value("${spring.datasource.r2dbc.username}")
    private String username;

    @Value("${spring.datasource.r2dbc.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        return ConnectionFactoryBuilder.withUrl(url)
                .username(username)
                .password(password)
                .build();
    }
}*/
