package com.github.jon7even.service.in.auth;

import com.github.jon7even.dto.UserShortDto;

/**
 * Интерфейс сервиса авторизации пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
public interface AuthorizationService {
    /**
     * Метод для авторизации пользователей при простых сообщений
     *
     * @param userShortDto из системы для проверки пользователя
     * @return boolean с ответом - авторизован ли пользователь
     */
    boolean processAuthorization(UserShortDto userShortDto);

    /**
     * Метод для авторизации пользователей при простых сообщений
     *
     * @param userId ID пользователя из системы
     * @return boolean с ответом - авторизован ли пользователь
     */
    boolean processAuthorizationForCallBack(Long userId);
}
