package com.github.jon7even.service.in.auth.cache.impl;

import com.github.jon7even.setup.PreparationForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Тестирование сервиса кэша авторизованных пользователей {@link UserAuthTrueCacheImpl}
 *
 * @author Jon7even
 * @version 2.0
 */
@DisplayName("Тестирование методов сервиса UserAuthTrueCacheImpl")
public class UserAuthTrueCacheImplTest extends PreparationForTests {

    private UserAuthTrueCacheImpl userAuthTrueCache;

    @BeforeEach
    public void setUp() {
        initUserDto();
        userAuthTrueCache = new UserAuthTrueCacheImpl(new HashMap<>(3));
    }

    @Test
    @DisplayName("Должен сохранить авторизованного пользователя в кэше")
    public void saveUserInCache_WhenUserSaveInCacheWithValidDates_Success() {
        userAuthTrueCache.saveUserInCache(userAuthTrueDtoOne);

        assertThat(userAuthTrueCache.isExistUserInCache(userAuthTrueDtoOne.getChatId()))
                .isTrue();
    }

    @Test
    @DisplayName("Должен удалить авторизованного пользователя из кэша по chatId")
    public void deleteUserFromAuthCache_Success() {
        userAuthTrueCache.saveUserInCache(userAuthTrueDtoOne);
        userAuthTrueCache.deleteUserFromAuthCache(userAuthTrueDtoOne.getChatId());

        assertThat(userAuthTrueCache.isExistUserInCache(userAuthTrueDtoOne.getChatId()))
                .isFalse();
    }

    @Test
    @DisplayName("Должен вернуть False, если авторизованный пользователь с таким chatId не существует в кэше")
    public void isExistUserInCache_WhenUserIsNotExistInCache_ReturnsFalse() {
        assertThat(userAuthTrueCache.isExistUserInCache(userAuthTrueDtoOne.getChatId()))
                .isFalse();
    }
}