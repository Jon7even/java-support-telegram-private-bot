package com.github.jon7even.cache;

import com.github.jon7even.telegram.BotState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Реализация сервиса кэширования пользователей {@link UserDataCache}
 *
 * @author Jon7even
 * @version 2.0
 */
@Component
@RequiredArgsConstructor
public class UserDataCacheImpl implements UserDataCache {

    private final HashMap<Long, BotState> botStatesOfUsers;

    @Override
    public void setBotStateForCacheUser(Long userId, BotState botState) {
        botStatesOfUsers.put(userId, botState);
    }

    @Override
    public BotState getBotStateFromCache(Long userId) {
        BotState state = botStatesOfUsers.get(userId);

        if (state == null) {
            state = BotState.MAIN_HELP;
        }

        return state;
    }
}