package com.github.jon7even.service.in.handle.factory;

import com.github.jon7even.service.in.handle.UserHandlerService;
import com.github.jon7even.service.in.status.UserStatusService;
import com.github.jon7even.telegram.BotState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Фабрика для выдачи реализации необходимого обработчика пользователю.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Для выдачи правильного обработчика используется статус {@link BotState},
 * который возвращает сервис {@link UserStatusService}.
 */
@Slf4j
@Component
public class UserHandlerFactory {

    private final HashMap<BotState, UserHandlerService> mapOfHandlersForUser;

    @Autowired
    public UserHandlerFactory() {
        this.mapOfHandlersForUser = new HashMap<>();
    }

    public UserHandlerService getHandlerForUser(BotState state) {
        return mapOfHandlersForUser.getOrDefault(state, null);
    }
}