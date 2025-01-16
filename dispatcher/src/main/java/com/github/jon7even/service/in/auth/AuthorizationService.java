package com.github.jon7even.service.in.auth;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс сервиса авторизации пользователей.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Идентифицирует пользователя, проводит процедуру аутентификации и в зависимости от разрешений производит
 * авторизацию пользователей.
 */
public interface AuthorizationService {

    /**
     * Метод для прохождения процедуры идентификации, аутентификации с последующей авторизации пользователей.
     *
     * @param update объект {@link Update} из Telegram API.
     * @return boolean - авторизован ли пользователь
     */
    boolean processAuthorization(Update update);
}