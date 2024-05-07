package com.github.jon7even.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс возможных типов передачи данных для RabbitMQ
 * Если в списке нет необходимого типа, его можно добавить
 *
 * @author Jon7even
 * @version 1.0
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
