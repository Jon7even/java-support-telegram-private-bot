package com.github.jon7even.cache;

import com.github.jon7even.telegram.BotState;

/**
 * Интерфейс сервиса отвечающего за кэш пользователя
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote конструирование новых моделей, хранение состояния бота для конкретного пользователя
 */
public interface UserDataCache {

    void setBotStateForCacheUser(Long userId, BotState botState);

    BotState getBotStateFromCache(Long userId);
}