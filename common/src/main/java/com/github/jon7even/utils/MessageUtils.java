package com.github.jon7even.utils;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@UtilityClass
public class MessageUtils {
    public SendMessage buildAnswerWithMessage(Message message,
                                              String text) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(text)
                .build();
    }

    public SendMessage buildAnswerWithText(Long chatId,
                                           String textToSend) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(textToSend)
                .build();
    }

    public SendMessage buildAnswerWithTextAndMarkup(Long chatId,
                                                    String textToSend,
                                                    InlineKeyboardMarkup inlineKeyboardMarkup) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(textToSend)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
    }

    public EditMessageText buildAnswerWithEditText(Long chatId,
                                                   String textToEdit,
                                                   Integer messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .text(textToEdit)
                .messageId(Math.toIntExact(messageId))
                .build();
    }

}
