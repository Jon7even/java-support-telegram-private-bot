package com.github.jon7even.service.in;

import com.github.jon7even.dto.UserAuthFalseDto;
import com.github.jon7even.dto.UserAuthTrueDto;
import com.github.jon7even.dto.UserCreateDto;
import com.github.jon7even.dto.UserUpdateDto;

/**
 * Интерфейс сервиса для взаимодействия с пользователями.
 *
 * @author Jon7even
 * @version 2.0
 */
public interface UserService {

    /**
     * Метод регистрирующий нового пользователя в системе.
     *
     * @param userCreateDto заполненный объект {@link UserCreateDto}
     * @return UserAuthFalseDto объект {@link UserAuthFalseDto} для дальнейшей авторизации пользователя
     */
    UserAuthFalseDto createUser(UserCreateDto userCreateDto);

    /**
     * Метод обновляющий данные существующего пользователя.
     *
     * @param userUpdateDto заполненный объект {@link UserUpdateDto}
     * @return UserAuthFalseDto объект {@link UserAuthFalseDto} для дальнейшей авторизации пользователя
     */
    UserAuthFalseDto updateUser(UserUpdateDto userUpdateDto);

    /**
     * Метод проверяющий имеется ли пользователь в БД.
     *
     * @param chatId - это chatId, который присваивает Telegram
     * @return boolean есть ли пользователь в системе
     */
    boolean isExistUserByChatId(Long chatId);

    /**
     * Метод изменяющий поле authorization у пользователя на true - пользователь авторизован.
     *
     * @param chatId - это chatId, который присваивает Telegram
     * @return UserAuthTrueDto объект {@link UserAuthTrueDto} для сохранения сессии в кэше
     */
    UserAuthTrueDto setAuthorizationTrue(Long chatId);
}