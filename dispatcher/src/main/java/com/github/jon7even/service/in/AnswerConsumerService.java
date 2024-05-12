package com.github.jon7even.service.in;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Интерфейс сервиса слушателя RabbitMq для отправки сообщений в Telegram
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote все что происходит в других сервисах обработки сообщений поступает в RabbitMq, который тем временем
 * проталкивает сообщения в этот сервис и он в свою очередь должен отправить обработанные ответы
 */
public interface AnswerConsumerService {
    /**
     * Метод для обработки входящих сообщений от RabbitMq (возвращенные ответы из других сервисов)
     *
     * @param sendMessage заполненное сообщение, готовое к отправке с обязательным chatId
     */
    void consume(SendMessage sendMessage);
}
