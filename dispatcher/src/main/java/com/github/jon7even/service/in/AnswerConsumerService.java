package com.github.jon7even.service.in;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Интерфейс сервиса слушателя RabbitMq для дальнейшей отправки сообщений в API Telegram.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Все что происходит в других сервисах обработки сообщений поступает в RabbitMq, который тем временем
 * проталкивает сообщения в этот сервис и он в свою очередь должен отправить обработанные ответы
 */
public interface AnswerConsumerService {

    /**
     * Метод для обработки входящих сообщений от RabbitMq (возвращенные ответы из других сервисов)
     *
     * @param sendMessage объект {@link SendMessage} корректно-заполненное сообщение, готовое к отправке. Обязательно
     *                    должно содержать chatId пользователя, который когда-либо писал в бот.
     */
    void consume(SendMessage sendMessage);
}