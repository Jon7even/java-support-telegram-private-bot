package com.github.jon7even.service.in.consumer;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс сервиса слушателя очередей RabbitMq входящих сообщений от API Telegram для их дальнейшей обработки
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Основная точка поступления данных в сервис "Узел"
 */
public interface ConsumerService {

    /**
     * Метод для обработки входящих текстовых сообщений
     */
    void consumeTextMessageUpdates(Update update);

    /**
     * Метод для обработки процесса нажатия на клавиатуру
     */
    void consumeCallbackQueryUpdates(Update update);

    /**
     * Метод для обработки входящих документов
     */
    void consumeDocMessageUpdates(Update update);

    /**
     * Метод для обработки входящих аудио сообщений
     */
    void consumeAudioMessageUpdates(Update update);

    /**
     * Метод для обработки входящих фото
     */
    void consumePhotoMessageUpdates(Update update);
}