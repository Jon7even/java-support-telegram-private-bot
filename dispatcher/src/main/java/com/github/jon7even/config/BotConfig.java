package com.github.jon7even.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "supportbot")
@PropertySource("classpath:supportbot.properties")
public class BotConfig {
    private String name;
    private String token;
    private String pass;
}