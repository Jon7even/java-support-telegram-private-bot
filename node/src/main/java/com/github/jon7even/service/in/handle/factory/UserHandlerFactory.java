package com.github.jon7even.service.in.handle.factory;

import com.github.jon7even.exception.IllegalHandlerException;
import com.github.jon7even.service.in.handle.UserHandlerService;
import com.github.jon7even.service.in.handle.impl.AskHandlerImpl;
import com.github.jon7even.service.in.handle.impl.StandardCallbackHandlerImpl;
import com.github.jon7even.service.in.handle.impl.StandardTextHandlerImpl;
import com.github.jon7even.service.in.status.UserStatusService;
import com.github.jon7even.telegram.BotState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

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

    public UserHandlerFactory(List<UserHandlerService> handlers) {
        this.mapOfHandlersForUser = new HashMap<>();
        initializeHandlers(handlers);
    }

    private void initializeHandlers(List<UserHandlerService> handlers) {
        for (UserHandlerService handler : handlers) {
            switch (handler) {
                case StandardTextHandlerImpl textHandler -> {
                    mapOfHandlersForUser.put(BotState.MAIN_START, textHandler);
                    mapOfHandlersForUser.put(BotState.MAIN_HELP, textHandler);
                }
                case StandardCallbackHandlerImpl callbackHandler -> {
                    mapOfHandlersForUser.put(BotState.MAIN_CALLBACK, callbackHandler);
                }
                case AskHandlerImpl askHandler -> {
                    mapOfHandlersForUser.put(BotState.MAIN_ASK, askHandler);
                }
                default -> {
                    log.error("Вы добавили новый UserHandlerService, но не определили для него логику в фабрике");
                    throw new IllegalHandlerException(handler.getClass().getName());
                }
            }
        }
    }

    public UserHandlerService getHandlerForUser(BotState state) {
        return mapOfHandlersForUser.getOrDefault(state, getDefaultHandler());
    }

    private UserHandlerService getDefaultHandler() {
        return mapOfHandlersForUser.get(BotState.MAIN_START);
    }
}