package com.github.jon7even.service.in.message;

/**
 * Интерфейс сервиса конвертации сообщений
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Использует таг сообщения из файла messages_ru_RU.properties и преобразует его в ответ
 */
public interface ReplyMessageService {

    String getReplyText(String replyText);

    String getReplyText(String replyText, Object... args);
}