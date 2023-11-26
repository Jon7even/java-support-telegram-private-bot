package com.github.jon7even.service.out.producer;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

/**
 * Интерфейс сервиса для отправки ответов в RabbitMq для дальнейшей обработки диспетчером
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Основная точка отправления данных из сервиса "Узел"
 */
public interface ProducerService {

    /**
     * Метод отправляющий ответ в виде текстового сообщения
     */
    void producerAnswerText(SendMessage sendMessage);

    /**
     * Метод отправляющий ответ в виде редактирования текущего сообщения, которое имеется в чате
     */
    void producerAnswerEditText(EditMessageText editMessageText);
}