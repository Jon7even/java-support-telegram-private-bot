package com.github.jon7even.service.producer;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface SenderMessageService {
    void sendText(Long chatId, String textToSend);

    void sendTextAndMarkup(Long chatId, String textToSend, InlineKeyboardMarkup inlineKeyboardMarkup);

    void sendEditText(Long chatId, String textToEdit, Integer messageId);

    void sendError(Long chatId, String textToError);
}
