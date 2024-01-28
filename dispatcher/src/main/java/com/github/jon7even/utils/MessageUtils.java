package com.github.jon7even.utils;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@UtilityClass
public class MessageUtils {
    public SendMessage buildAnswerWithText(Message message, String text) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(text)
                .build();
    }
}
