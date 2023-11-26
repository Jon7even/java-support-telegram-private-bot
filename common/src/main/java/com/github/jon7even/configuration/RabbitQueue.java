package com.github.jon7even.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс возможных типов передачи данных для RabbitMQ
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Если в списке нет необходимого типа, его можно добавить. Методы добавлены в базовую часть конфигурации
 * RabbitConfiguration, но многие из них не реализованы, работают как заглушка. Сделано это для удобства, чтобы
 * оперативно вносить изменения и реализовывать новый функционал.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RabbitQueue {
    public static final String TEXT_MESSAGE_UPDATE = "text_message_update";
    public static final String CALLBACK_QUERY_UPDATE = "callback_query_update";
    public static final String DOC_MESSAGE_UPDATE = "doc_message_update";
    public static final String PHOTO_MESSAGE_UPDATE = "photo_message_update";
    public static final String AUDIO_MESSAGE_UPDATE = "audio_message_update";
    public static final String ANSWER_MESSAGE = "answer_message";
}