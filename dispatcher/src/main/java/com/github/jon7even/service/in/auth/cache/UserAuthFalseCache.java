package com.github.jon7even.service.in.auth.cache;

import com.github.jon7even.dto.UserAuthFalseDto;

/**
 * Интерфейс сервиса хранения неавторизованных пользователей в кеше
 *
 * @author Jon7even
 * @version 1.0
 */
public interface UserAuthFalseCache {
    /**
     * Метод сохранения пользователя в кеше
     *
     * @param userAuthFalseDto заполненный объект DTO
     */
    void saveUserInCache(UserAuthFalseDto userAuthFalseDto);

    /**
     * Метод увеличения счетчика количества попыток пользователя ввести пароль
     *
     * @param chatIdUser  - это chatId, который присваивает Telegram
     * @param attemptAuth текущее количество попыток
     * @return int увеличенное количество попыток
     */
    int increaseAttemptAuthToCache(Long chatIdUser, int attemptAuth);

    /**
     * Метод получения текущего количества попыток пользователя ввести пароль
     *
     * @param chatIdUser - это chatId, который присваивает Telegram
     * @return int текущее количество попыток
     */
    int getAttemptsAuthFromCache(Long chatIdUser);

    /**
     * Метод удаления пользователя из кеша после успешной авторизации
     *
     * @param chatIdUser - это chatId, который присваивает Telegram
     */
    void deleteUserFromAuthCache(Long chatIdUser);
}
