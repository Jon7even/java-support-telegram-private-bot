package com.github.jon7even.service.in;

import com.github.jon7even.dto.UserAuthFalseDto;
import com.github.jon7even.dto.UserAuthTrueDto;
import com.github.jon7even.dto.UserCreateDto;

/**
 * Интерфейс сервиса для взаимодействия с пользователями
 *
 * @author Jon7even
 * @version 1.0
 */
public interface UserService {
    /**
     * Метод регистрирующий нового пользователя в системе
     *
     * @param userCreateDto заполненный объект DTO
     * @return UserAuthFalseDto объект DTO для дальнейшей авторизации пользователя
     */
    UserAuthFalseDto createUser(UserCreateDto userCreateDto);

    /**
     * Метод проверяющий имеется ли пользователь в БД
     *
     * @param chatId - это chatId, который присваивает Telegram
     * @return boolean ответ
     */
    boolean isExistUserByChatId(Long chatId);

    /**
     * Метод изменяющий поле authorization у пользователя на true - пользователь авторизовался
     *
     * @param chatId - это chatId, который присваивает Telegram
     * @return UserAuthTrueDto объект DTO для сохранения сессии в кэше
     */
    UserAuthTrueDto setAuthorizationTrue(Long chatId);
}
