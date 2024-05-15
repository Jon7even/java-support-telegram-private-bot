package com.github.jon7even.service.in.auth;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс сервиса авторизации пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
public interface AuthorizationService {
    /**
     * Метод для авторизации пользователей
     *
     * @param update из системы для проверки пользователя
     * @return boolean с ответом - авторизован ли пользователь
     */
    boolean processAuthorization(Update update);
}
