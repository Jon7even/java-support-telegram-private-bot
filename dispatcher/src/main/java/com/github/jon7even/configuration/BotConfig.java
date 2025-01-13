package com.github.jon7even.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Утилитарный класс загружающий токен и имя бота из application.yaml.
 *
 * @author Jon7even
 * @version 2.0
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("bot.token")
public class BotConfig {
    private String name;
    private String token;
}