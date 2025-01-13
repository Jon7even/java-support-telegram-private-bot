package com.github.jon7even.controller.in;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс для взаимодействия с обработкой {@link Update} от Telegram API.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote
 */
public interface HandleUpdateBot {

    /**
     * Метод для обработки сообщений
     *
     * @param update объект, который пришел от Telegram.
     */
    void processUpdate(Update update);
}