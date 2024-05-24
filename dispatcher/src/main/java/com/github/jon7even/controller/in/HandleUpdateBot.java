package com.github.jon7even.controller.in;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс для взаимодействия с обработкой входящих сообщений
 *
 * @author Jon7even
 * @version 1.0
 */
public interface HandleUpdateBot {
    /**
     * Метод для обработки сообщений
     *
     * @param update который пришел от Telegram
     */
    void processUpdate(Update update);
}
