package com.github.jon7even.service.in.consumer;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс сервиса слушателя очередей RabbitMq входящих сообщений от сервиса Dispatcher для их дальнейшей обработки.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Основная точка поступления данных в сервис "Node - Узел".
 */
public interface ConsumerService {

    /**
     * Метод для обработки входящих текстовых сообщений
     *
     * @param update заполненный {@link Update} из Telegram API
     */
    void consumeTextMessageUpdates(Update update);

    /**
     * Метод для обработки процесса нажатия на клавиатуру
     *
     * @param update заполненный {@link Update} из Telegram API
     */
    void consumeCallbackQueryUpdates(Update update);

    /**
     * Метод для обработки входящих документов
     *
     * @param update заполненный {@link Update} из Telegram API
     */
    void consumeDocMessageUpdates(Update update);

    /**
     * Метод для обработки входящих аудио сообщений
     *
     * @param update заполненный {@link Update} из Telegram API
     */
    void consumeAudioMessageUpdates(Update update);

    /**
     * Метод для обработки входящих фото
     *
     * @param update заполненный {@link Update} из Telegram API
     */
    void consumePhotoMessageUpdates(Update update);
}