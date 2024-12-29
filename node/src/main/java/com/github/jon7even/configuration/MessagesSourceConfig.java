package com.github.jon7even.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Загрузка конфигурации языковой поддержки приложения
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote файл конфигурации языковой поддержки называется messages_ru_RU.properties, в нем находится RU локализация
 */
@Configuration
public class MessagesSourceConfig {
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
