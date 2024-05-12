package com.github.jon7even.service.in.auth.cache;

/**
 * Интерфейс сервиса хранения пользователей в кеше для сервиса авторизации
 *
 * @author Jon7even
 * @version 1.0
 */
public interface UserAuthCache {
    /**
     * Метод сохранения количества попыток пользователя ввести пароль
     *
     * @param userId      существующий ID пользователя
     * @param attemptAuth количество попыток
     */
    void setAttemptAuthForUserCache(Long userId, Integer attemptAuth);

    /**
     * Метод получения текущего количества попыток пользователя ввести пароль
     *
     * @param userId существующий ID пользователя
     * @return int текущее количество попыток
     */
    int getAttemptsAuthForUserCache(Long userId);

    /**
     * Метод удаления пользователя из кеша после успешной авторизации
     *
     * @param userId существующий ID пользователя
     */
    void deleteUserFromAuthCache(Long userId);
}
