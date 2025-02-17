package com.github.jon7even.service.in;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс сервиса быстрой обработки основных команд бота.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Чтобы снизить нагрузку и отсечь использование RabbitMq используется этот сервис. В него можно добавлять
 * обработку статичных методов типа /help.
 */
public interface MainQuickService {

    /**
     * Метод для проверки команды от пользователя на его тип.
     *
     * @param command команда пользователя
     * @return boolean базовая команда или нет
     */
    boolean existsBaseCommand(String command);

    /**
     * Метод для обработки и отправки статичного сообщения пользователю.
     *
     * @param update заполненный объект {@link Update} из Telegram для формирования шаблона ответа
     * @return заполненный объект {@link SendMessage} для отправления ответа пользователю
     */
    SendMessage processQuickAnswer(Update update);
}