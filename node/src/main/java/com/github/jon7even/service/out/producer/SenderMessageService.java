package com.github.jon7even.service.out.producer;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * Интерфейс утилитарного сервиса, помогающий конструировать вид ответа для дальнейшей отправки в RabbitMq.
 *
 * @author Jon7even
 * @version 2.0
 */
public interface SenderMessageService {

    /**
     * Метод для ответа в виде текста.
     *
     * @param chatId     ID пользователя из Telegram
     * @param textToSend текст, который будет отправлен пользователю в чат
     */
    void sendText(Long chatId, String textToSend);

    /**
     * Метод для ответа в виде текста и отправки клавиатуры.
     *
     * @param chatId               ID пользователя из Telegram
     * @param textToSend           текст, который будет отправлен пользователю в чат
     * @param inlineKeyboardMarkup объект с {@link InlineKeyboardMarkup} заполненная новая клавиатура
     */
    void sendTextAndMarkup(Long chatId, String textToSend, InlineKeyboardMarkup inlineKeyboardMarkup);

    /**
     * Метод для ответа в виде текста и редактирования имеющегося в чате Бота сообщения.
     *
     * @param chatId     ID пользователя из Telegram
     * @param textToEdit текст, который будет отредактирован у пользователя в чате
     * @param messageId  ID сообщения в конкретном чате с пользователем
     */
    void sendEditText(Long chatId, String textToEdit, Integer messageId);

    /**
     * Метод для отправления возможных ошибок
     *
     * @param chatId      ID пользователя из Telegram
     * @param textToError текст с ошибкой, который будет отправлен пользователю в чат
     */
    void sendError(Long chatId, String textToError);
}