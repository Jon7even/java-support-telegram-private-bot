package com.github.jon7even.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@ConfigurationProperties("bot.security")
public class SecurityConfig {
    private String keyPass;
    private Integer attemptsAuth;
}
