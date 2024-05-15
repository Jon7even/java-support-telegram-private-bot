package com.github.jon7even.service.in.auth.cache;

import com.github.jon7even.dto.UserAuthFalseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Реализация сервиса хранения неавторизованных пользователей в кеше
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote На текущий момент реализация состоит из хранения данных в памяти. А это значит, что при рестарте приложения
 * всем пользователям нужно будет вводить пароль заново. На текущий момент это работает как фича, т.к. когда
 * пользователь будет вводить пароль заново, его ник или имя (если они менялись), будут обновлены в БД. Но если
 * реализацию сделать на NoSQL базах, нужно продумать дешевый вариант обновления данных о пользователе в БД.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserAuthFalseCacheImpl implements UserAuthFalseCache {
    private final HashMap<Long, UserAuthFalseDto> mapOfUsersAuthFalse;

    @Override
    public void saveUserInCache(UserAuthFalseDto userAuthFalseDto) {
        mapOfUsersAuthFalse.put(userAuthFalseDto.getChatId(), userAuthFalseDto);
        log.debug("Пользователь [userAuthFalseDto={}] сохранен в кеше", userAuthFalseDto);
    }

    @Override
    public int increaseAttemptAuthToCache(Long chatIdUser, int attemptAuth) {
        log.debug("Начинаем увеличивать счетчик количества попыток ввести пароль, "
                + "текущий [attemptAuth={}] для пользователя [chatIdUser={}]", attemptAuth, chatIdUser);
        var userFromCache = mapOfUsersAuthFalse.get(chatIdUser);
        userFromCache.setAttemptAuth(++attemptAuth);
        mapOfUsersAuthFalse.put(chatIdUser, userFromCache);
        var currentAuthAttempts = getAttemptsAuthFromCache(chatIdUser);
        log.debug("Счетчик для пользователя [chatIdUser={}] увеличен, теперь он [attemptAuth={}]",
                chatIdUser, currentAuthAttempts);
        return currentAuthAttempts;
    }

    @Override
    public int getAttemptsAuthFromCache(Long chatIdUser) {
        var currentCountAttempt = mapOfUsersAuthFalse.get(chatIdUser).getAttemptAuth();
        log.debug("Текущее количество попыток ввести пароль: [{}] для пользователя с [chatIdUser={}]",
                currentCountAttempt, chatIdUser);
        return currentCountAttempt;
    }

    @Override
    public void deleteUserFromAuthCache(Long chatIdUser) {
        log.debug("Пользователь с [chatIdUser={}] ввел пароль правильно, удаляем данные", chatIdUser);
        mapOfUsersAuthFalse.remove(chatIdUser);
    }
}
