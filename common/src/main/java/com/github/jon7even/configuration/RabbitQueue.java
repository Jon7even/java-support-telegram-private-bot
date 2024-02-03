package com.github.jon7even.configuration;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RabbitQueue {
    public final String TEXT_MESSAGE_UPDATE = "text_message_update";
    public final String CALLBACK_QUERY_UPDATE = "callback_query_update";
    public final String DOC_MESSAGE_UPDATE = "doc_message_update";
    public final String PHOTO_MESSAGE_UPDATE = "photo_message_update";
    public final String AUDIO_MESSAGE_UPDATE = "audio_message_update";
    public final String ANSWER_MESSAGE = "answer_message";
}
