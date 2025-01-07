package com.github.jon7even.service.in.auth.cache.impl;

import com.github.jon7even.dto.UserAuthFalseDto;
import com.github.jon7even.exception.AlreadyExistException;
import com.github.jon7even.service.in.auth.cache.UserAuthFalseCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Реализация сервиса хранения неавторизованных пользователей в кэше
 *
 * @author Jon7even
 * @version 2.0
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
    public void saveUserInCache(UserAuthFalseDto userAuthFalseDto) throws AlreadyExistException {
        log.trace("Начинаю сохранять пользователя [userAuthFalseDto={}] в кэше неавторизованных пользователей",
                userAuthFalseDto);
        if (!mapOfUsersAuthFalse.containsKey(userAuthFalseDto.getChatId())) {
            mapOfUsersAuthFalse.put(userAuthFalseDto.getChatId(), userAuthFalseDto);
            log.debug("Пользователь [userAuthFalseDto={}] сохранен в кэше неавторизованных пользователей",
                    userAuthFalseDto);
        } else {
            log.error("Пользователь [userAuthFalseDto={}] не сохранен в кэше неавторизованных пользователей, т.к."
                    + "уже здесь есть", userAuthFalseDto);
            throw new AlreadyExistException(userAuthFalseDto.toString());
        }
    }

    @Override
    public boolean isExistUserInCache(Long chatIdUser) {
        log.trace("Проверяем есть ли пользователь с [chatIdUser={}] в кэше неавторизованных пользователей", chatIdUser);
        return mapOfUsersAuthFalse.containsKey(chatIdUser);
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
        mapOfUsersAuthFalse.remove(chatIdUser);
        log.debug("Пользователь с [chatIdUser={}] удален из кэша неавторизованных пользователей", chatIdUser);
    }
}