package com.github.jon7even.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = "privatebot")
@PropertySource("classpath:privatebot.properties")
public class BotConfig {
    private String name;
    private String token;
}
