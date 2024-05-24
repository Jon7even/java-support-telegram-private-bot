package com.github.jon7even.service.in.auth.cache;

import com.github.jon7even.dto.UserAuthTrueDto;

/**
 * Интерфейс сервиса хранения авторизованных пользователей в кэше
 *
 * @author Jon7even
 * @version 1.0
 */
public interface UserAuthTrueCache {
    /**
     * Метод сохранения пользователя в кэше
     *
     * @param userAuthTrueDto заполненный объект DTO
     */
    void saveUserInCache(UserAuthTrueDto userAuthTrueDto);

    /**
     * Метод проверяющий имеется ли пользователь в кэше авторизованных пользователей
     *
     * @param chatIdUser - это chatId, который присваивает Telegram
     * @return boolean ответ
     */
    boolean isExistUserInCache(Long chatIdUser);

    /**
     * Метод удаления пользователя из кэша (выход из аккаунта)
     *
     * @param chatIdUser - это chatId, который присваивает Telegram
     */
    void deleteUserFromAuthCache(Long chatIdUser);
}
