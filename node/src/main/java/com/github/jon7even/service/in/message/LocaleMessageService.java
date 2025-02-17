package com.github.jon7even.service.in.message;

/**
 * Интерфейс сервиса языковой локализации конвертации тагов в требуемые сообщения.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Обязательно должен быть файл настроек messages_ru_RU.properties и конфигурация MessagesSourceConfig.
 */
public interface LocaleMessageService {

    /**
     * Метод для получения сообщения по его тагу.
     *
     * @param replyText существующий таг, который требуется получить
     * @return обработанный String для ответа
     */
    String getMessage(String replyText);

    /**
     * Метод для получения сообщения по его тагу с подстановкой аргументов.
     *
     * @param replyText существующий таг, который требуется получить
     * @param args      аргументы, которые требуется заменить в сообщении
     * @return обработанный String для ответа
     */
    String getMessage(String replyText, Object... args);
}