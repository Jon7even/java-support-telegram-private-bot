package com.github.jon7even.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * Утилитарный класс для сборки ответа в Telegram для дальнейшей отправки.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Включает обновления 8й версии telegrambots.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageUtils {

    public static SendMessage buildAnswerWithMessage(Message message,
                                                     String text) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(text)
                .build();
    }

    public static SendMessage buildAnswerWithText(Long chatId,
                                                  String textToSend) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(textToSend)
                .build();
    }

    public static SendMessage buildAnswerWithTextAndMarkup(Long chatId,
                                                           String textToSend,
                                                           InlineKeyboardMarkup inlineKeyboardMarkup) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(textToSend)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
    }

    public static EditMessageText buildAnswerWithEditText(Long chatId,
                                                          String textToEdit,
                                                          Integer messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .text(textToEdit)
                .messageId(Math.toIntExact(messageId))
                .build();
    }
}