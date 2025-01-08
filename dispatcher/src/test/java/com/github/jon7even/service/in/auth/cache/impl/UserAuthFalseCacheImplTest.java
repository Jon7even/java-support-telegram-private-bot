package com.github.jon7even.service.in.auth.cache.impl;

import com.github.jon7even.exception.AlreadyExistException;
import com.github.jon7even.setup.PreparationForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 * Тестирование сервиса кэша неавторизованных пользователей {@link UserAuthFalseCacheImpl}
 *
 * @author Jon7even
 * @version 2.0
 */
@DisplayName("Тестирование методов сервиса UserAuthFalseCacheImpl")
public class UserAuthFalseCacheImplTest extends PreparationForTests {

    private UserAuthFalseCacheImpl authFalseCache;

    @BeforeEach
    void setUp() {
        initUserDto();
        authFalseCache = new UserAuthFalseCacheImpl(new HashMap<>(3));
    }

    @Test
    @DisplayName("Должен сохранить неавторизованного пользователя в кэше")
    void saveUserInCache_WhenUserSaveInCacheWithValidDates_Success() {
        authFalseCache.saveUserInCache(userAuthFalseDtoOne);

        assertThat(authFalseCache.isExistUserInCache(userAuthFalseDtoOne.getChatId()))
                .isTrue();
    }

    @Test
    @DisplayName("Должен у неавторизованного пользователя вернуть правильное текущее количество попыток входа - 0")
    void getAttemptsAuthFromCache_WhenUserHasDefaultCountAttempt_ReturnsZero() {
        int expectedCountAttempt = 0;
        authFalseCache.saveUserInCache(userAuthFalseDtoOne);

        assertThat(authFalseCache.getAttemptsAuthFromCache(userAuthFalseDtoOne.getChatId()))
                .isEqualTo(expectedCountAttempt);
    }

    @Test
    @DisplayName("Должен у неавторизованного пользователя увеличить счетчик попыток входа на 1")
    void increaseAttemptAuthToCache_WhenUserIncreaseAttemptAuthToCache_ReturnsOne() {
        int expectedCountAttempt = 1;
        authFalseCache.saveUserInCache(userAuthFalseDtoOne);
        authFalseCache.increaseAttemptAuthToCache(
                userAuthFalseDtoOne.getChatId(), userAuthFalseDtoOne.getAttemptAuth()
        );

        assertThat(authFalseCache.getAttemptsAuthFromCache(userAuthFalseDtoOne.getChatId()))
                .isEqualTo(expectedCountAttempt);
    }

    @Test
    @DisplayName("Должен удалить неавторизованного пользователя из кэша по chatId")
    void deleteUserFromAuthCache_Success() {
        authFalseCache.saveUserInCache(userAuthFalseDtoOne);
        authFalseCache.deleteUserFromAuthCache(userAuthFalseDtoOne.getChatId());

        assertThat(authFalseCache.isExistUserInCache(userAuthFalseDtoOne.getChatId()))
                .isFalse();
    }

    @Test
    @DisplayName("Должен вернуть False, если неавторизованный пользователь с таким chatId не существует в кэше")
    void isExistUserInCache_WhenUserIsNotExistInCache_ReturnsFalse() {
        assertThat(authFalseCache.isExistUserInCache(userAuthFalseDtoOne.getChatId()))
                .isFalse();
    }

    @Test
    @DisplayName("Не должен сохранить неавторизованного пользователя в кэше: такой пользователь уже существует")
    void saveUserInCache_WhenUserAlreadyExist_ThrowsAlreadyExistException() {
        authFalseCache.saveUserInCache(userAuthFalseDtoOne);

        String expectedErrorMessage = String.format("[%s] already exist", userAuthFalseDtoOne.toString());

        assertThatThrownBy(() -> authFalseCache.saveUserInCache(userAuthFalseDtoOne))
                .isInstanceOf(AlreadyExistException.class)
                .hasMessage(expectedErrorMessage);
    }
}