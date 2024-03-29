package com.github.jon7even.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAuthCacheImpl implements UserAuthCache {
    private final HashMap<Long, Integer> attemptsAuthOfUsers;

    @Override
    public void setAttemptAuthForUserCache(Long userId, Integer attemptAuth) {
        log.debug("Пользователь userId={} вводит пароль, текущий count={}", userId, attemptAuth);
        attemptsAuthOfUsers.put(userId, attemptAuth);
    }

    @Override
    public int getAttemptsAuthForUserCache(Long userId) {
        return attemptsAuthOfUsers.getOrDefault(userId, 0);
    }

    @Override
    public void deleteUserFromAuthCache(Long userId) {
        log.debug("Пользователь userId={} ввел пароль правильно, удаляем данные", userId);
        attemptsAuthOfUsers.remove(userId);
    }
}
