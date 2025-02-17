package com.github.jon7even.service.in.auth.cache;

import com.github.jon7even.dto.UserAuthFalseDto;

/**
 * Интерфейс сервиса хранения неавторизованных пользователей в кэше.
 *
 * @author Jon7even
 * @version 2.0
 */
public interface UserAuthFalseCache {

    /**
     * Метод сохранения пользователя в кэше.
     *
     * @param userAuthFalseDto заполненный объект {@link UserAuthFalseDto}
     */
    void saveUserInCache(UserAuthFalseDto userAuthFalseDto);

    /**
     * Метод проверяющий имеется ли пользователь в кэше неавторизованных пользователей.
     *
     * @param chatIdUser - это chatId, который присваивает Telegram
     * @return boolean ответ
     */
    boolean isExistUserInCache(Long chatIdUser);

    /**
     * Метод увеличения счетчика количества попыток пользователя ввести пароль.
     *
     * @param chatIdUser  - это chatId, который присваивает Telegram
     * @param attemptAuth текущее количество попыток
     * @return int актуальное количество попыток
     */
    int increaseAttemptAuthToCache(Long chatIdUser, int attemptAuth);

    /**
     * Метод получения текущего количества попыток пользователя ввести пароль.
     *
     * @param chatIdUser - это chatId, который присваивает Telegram
     * @return int актуальное количество попыток
     */
    int getAttemptsAuthFromCache(Long chatIdUser);

    /**
     * Метод удаления пользователя из кэша после успешной авторизации.
     *
     * @param chatIdUser - это chatId, который присваивает Telegram
     */
    void deleteUserFromAuthCache(Long chatIdUser);
}