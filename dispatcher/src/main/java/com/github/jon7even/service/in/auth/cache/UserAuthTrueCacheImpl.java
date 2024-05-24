package com.github.jon7even.service.in.auth.cache;

import com.github.jon7even.dto.UserAuthTrueDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Реализация сервиса хранения авторизованных пользователей в кэше
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
public class UserAuthTrueCacheImpl implements UserAuthTrueCache {
    private final HashMap<Long, UserAuthTrueDto> mapOfUsersAuthTrue;

    @Override
    public void saveUserInCache(UserAuthTrueDto userAuthTrueDto) {
        mapOfUsersAuthTrue.put(userAuthTrueDto.getChatId(), userAuthTrueDto);
        log.debug("Пользователь [userAuthTrueDto={}] сохранен в кэше авторизованных пользователей", userAuthTrueDto);
    }

    @Override
    public boolean isExistUserInCache(Long chatIdUser) {
        log.trace("Проверяем есть ли пользователь с [chatIdUser={}] в кэше авторизованных пользователей", chatIdUser);
        return mapOfUsersAuthTrue.containsKey(chatIdUser);
    }

    @Override
    public void deleteUserFromAuthCache(Long chatIdUser) {
        mapOfUsersAuthTrue.remove(chatIdUser);
        log.debug("Пользователь с [chatIdUser={}] удален из кэша авторизованных пользователей", chatIdUser);
    }
}
