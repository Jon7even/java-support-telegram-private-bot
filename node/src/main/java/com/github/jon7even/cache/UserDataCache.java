package com.github.jon7even.cache;

import com.github.jon7even.dto.company.CompanyBuildingDto;
import com.github.jon7even.telegram.BotState;

/**
 * Интерфейс сервиса отвечающего за кеш пользователя (конструирование новых моделей, хранение состояния бота для
 * конкретного пользователя)
 *
 * @author Jon7even
 * @version 1.0
 */
public interface UserDataCache {
    void setBotStateForCacheUser(Long userId, BotState botState);

    void setCompanyForCacheUser(Long userId, CompanyBuildingDto companyBuildingDto);

    BotState getBotStateFromCache(Long userId);

    CompanyBuildingDto getCompanyFromCache(Long userId);
}
