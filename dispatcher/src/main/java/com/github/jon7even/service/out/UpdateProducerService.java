package com.github.jon7even.service.out;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс сервиса постановки запросов HandleBotController в очередь RabbitMq
 *
 * @author Jon7even
 * @version 2.0
 */
public interface UpdateProducerService {

    /**
     * Метод для обработки входящих текстовых сообщений от пользователей бота
     *
     * @param rabbitQueue название очереди
     * @param update      заполненный Update из Telegram для дальнейшей обработки
     */
    void produceText(String rabbitQueue, Update update);

    /**
     * Метод для обработки нажатой кнопки на клавиатуре в чате от пользователей бота
     *
     * @param rabbitQueue название очереди
     * @param update      заполненный Update из Telegram для дальнейшей обработки
     */
    void produceCallBackQuery(String rabbitQueue, Update update);
}