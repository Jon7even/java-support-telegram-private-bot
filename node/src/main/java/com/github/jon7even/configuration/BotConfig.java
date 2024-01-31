package com.github.jon7even.configuration;

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
    private String pass;
}
