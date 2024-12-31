package com.github.jon7even.service.in.message;

/**
 * Интерфейс сервиса языковой локализации
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Обязательно должен быть файл настроек messages_ru_RU.properties и конфигурация MessagesSourceConfig
 */
public interface LocaleMessageService {

    String getMessage(String message);

    String getMessage(String message, Object... args);
}