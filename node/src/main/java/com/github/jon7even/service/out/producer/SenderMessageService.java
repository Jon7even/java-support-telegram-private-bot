package com.github.jon7even.service.out.producer;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * Интерфейс утилитарного сервиса, помогающий конструировать вид ответа для дальнейшей отправки в RabbitMq
 *
 * @author Jon7even
 * @version 2.0
 */
public interface SenderMessageService {

    /**
     * Метод для ответа в виде текста
     */
    void sendText(Long chatId, String textToSend);

    /**
     * Метод для ответа в виде текста и отправки клавиатуры
     */
    void sendTextAndMarkup(Long chatId, String textToSend, InlineKeyboardMarkup inlineKeyboardMarkup);

    /**
     * Метод для ответа в виде текста и редактирования имеющегося в чате Бота сообщения
     */
    void sendEditText(Long chatId, String textToEdit, Integer messageId);

    /**
     * Метод для возможных ошибок
     */
    void sendError(Long chatId, String textToError);
}