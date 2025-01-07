package com.github.jon7even.service.in.auth.impl;

import com.github.jon7even.configuration.SecurityConfig;
import com.github.jon7even.controller.out.SenderBotClient;
import com.github.jon7even.dto.UserCreateDto;
import com.github.jon7even.mapper.UserMapper;
import com.github.jon7even.service.in.UserService;
import com.github.jon7even.service.in.auth.cache.UserAuthFalseCache;
import com.github.jon7even.service.in.auth.cache.UserAuthTrueCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Тестирование сервиса авторизация {@link AuthorizationServiceImpl}
 *
 * @author Jon7even
 * @version 2.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование методов сервиса AuthorizationServiceImpl")
public class AuthorizationServiceImplTest {

    @InjectMocks
    private AuthorizationServiceImpl authorizationService;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserAuthFalseCache userAuthFalseCache;

    @Mock
    private UserAuthTrueCache userAuthTrueCache;

    @Mock
    private SecurityConfig securityConfig;

    @Mock
    private SenderBotClient senderBotClient;

    private UserCreateDto correctUserShortDto;

    private UserCreateDto incorrectUserShortDto;

    @BeforeEach
    void setUp() {
        correctUserShortDto = UserCreateDto.builder()
                .chatId(1111111L)
                .firstName("FirstName")
                .lastName("LastName")
                .userName("UserNameOne")
                .build();

        incorrectUserShortDto = UserCreateDto.builder()
                .chatId(1111111L)
                .firstName("FirstName")
                .lastName("LastName")
                .userName("UserNameTwo")
                .build();
    }
}