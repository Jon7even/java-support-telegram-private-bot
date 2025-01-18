package com.github.jon7even.service.in.handle;

import com.github.jon7even.service.in.handle.factory.UserHandlerFactory;
import com.github.jon7even.service.in.status.UserStatusService;
import com.github.jon7even.telegram.BotState;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс сервиса, который обрабатывает запрос от пользователя.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Нужный обработчик выдается фабрикой {@link UserHandlerFactory}.
 * Для выдачи правильного обработчика используется статус {@link BotState},
 * который возвращает сервис {@link UserStatusService}.
 */
public interface UserHandlerService {

    /**
     * Метод для конечной обработки запроса пользователя.
     *
     * @param update заполненный {@link Update} из Telegram API, который передает {@link HandlerService}
     */
    void handle(Update update);
}