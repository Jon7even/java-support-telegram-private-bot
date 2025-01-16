package com.github.jon7even.service.in.message;

/**
 * Интерфейс сервиса получения необходимых сообщений для ответов из тагов.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Использует таг сообщения из файла messages_ru_RU.properties и преобразует его в ответ. Использует
 * {@link LocaleMessageService} для сокрытия слоя реализации, можно добавлять необходимые методы для использования
 * в разных частях приложения.
 */
public interface ReplyMessageService {

    /**
     * Метод для получения сообщения по его тагу.
     *
     * @param replyText существующий таг, который требуется получить
     * @return обработанный String для ответа
     */
    String getReplyText(String replyText);

    /**
     * Метод для получения сообщения по его тагу с подстановкой аргументов.
     *
     * @param replyText существующий таг, который требуется получить
     * @param args      аргументы, которые требуется заменить в сообщении
     * @return обработанный String для ответа
     */
    String getReplyText(String replyText, Object... args);
}