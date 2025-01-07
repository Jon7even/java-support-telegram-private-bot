package com.github.jon7even.service.in.auth.impl;

import com.github.jon7even.configuration.SecurityConfig;
import com.github.jon7even.controller.out.SenderBotClient;
import com.github.jon7even.mapper.UserMapper;
import com.github.jon7even.mapper.UserMapperImpl;
import com.github.jon7even.service.in.UserService;
import com.github.jon7even.service.in.auth.cache.UserAuthFalseCache;
import com.github.jon7even.service.in.auth.cache.UserAuthTrueCache;
import com.github.jon7even.setup.PreparationForTests;
import com.github.jon7even.utils.MessageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import static com.github.jon7even.telegram.constants.DefaultBaseMessagesToSend.USER_AUTH_TRUE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тестирование сервиса авторизация {@link AuthorizationServiceImpl}
 *
 * @author Jon7even
 * @version 2.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование методов сервиса AuthorizationServiceImpl")
public class AuthorizationServiceImplTest extends PreparationForTests {

    @InjectMocks
    private AuthorizationServiceImpl authorizationService;

    @Mock
    private UserService userService;

    @Mock
    private SecurityConfig securityConfig;

    @Mock
    private SenderBotClient senderBotClient;

    @Mock
    private UserAuthFalseCache userAuthFalseCache;

    @Mock
    private UserAuthTrueCache userAuthTrueCache;

    private final UserMapper userMapper = new UserMapperImpl();
    ;

    @Mock
    private Update update;

    private final String pass = "testPass";

    @BeforeEach
    void setUp() {
        initUserDto();
        when(securityConfig.getKeyPass()).thenReturn(pass);
        when(securityConfig.getAttemptsAuth()).thenReturn(2);
        authorizationService = new AuthorizationServiceImpl(
                userService, userMapper, userAuthFalseCache, userAuthTrueCache, securityConfig, senderBotClient
        );
    }

    @Test
    @DisplayName("Новый пользователь ввел правильный пароль и авторизовался")
    void processAuthorization_WhenNewUserSetValidPass_ReturnsTrue() {
        boolean expectedResult = true;
        long chatId = userCreateDtoOne.getChatId();
        int attemptsAuthUser = 0;
        Message testMessage = Message.builder()
                .chat(Chat.builder()
                        .id(chatId)
                        .type("private")
                        .firstName(userCreateDtoOne.getFirstName())
                        .lastName(userCreateDtoOne.getLastName())
                        .userName(userCreateDtoOne.getLastName())
                        .build())
                .text(pass)
                .build();
        SendMessage expectedMessage = MessageUtils.buildAnswerWithText(testMessage.getChatId(), USER_AUTH_TRUE);

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(testMessage);
        when(userAuthTrueCache.isExistUserInCache(chatId)).thenReturn(false);
        when(userService.isExistUserByChatId(chatId)).thenReturn(false);
        when(userService.createUser(userMapper.toDtoCreateFromMessage(testMessage.getChat())))
                .thenReturn(userAuthFalseDtoOne);
        doNothing().when(userAuthFalseCache).saveUserInCache(userAuthFalseDtoOne);
        when(userAuthFalseCache.isExistUserInCache(chatId)).thenReturn(true);
        when(userAuthFalseCache.getAttemptsAuthFromCache(chatId)).thenReturn(attemptsAuthUser);
        when(userAuthFalseCache.increaseAttemptAuthToCache(chatId, attemptsAuthUser)).thenReturn(1);
        when(userService.setAuthorizationTrue(chatId)).thenReturn(userAuthTrueDtoOne);
        doNothing().when(userAuthTrueCache).saveUserInCache(userAuthTrueDtoOne);
        doNothing().when(userAuthFalseCache).deleteUserFromAuthCache(chatId);
        doNothing().when(senderBotClient).sendAnswerMessage(expectedMessage);

        boolean resultAuth = authorizationService.processAuthorization(update);

        assertThat(resultAuth)
                .isEqualTo(expectedResult);

        verify(update, times(1)).hasMessage();
        verify(update, times(1)).getMessage();
        verify(userAuthTrueCache, times(1)).isExistUserInCache(chatId);
        verify(userService, times(1)).isExistUserByChatId(chatId);
        verify(userService, times(1)).createUser(userMapper.toDtoCreateFromMessage(testMessage.getChat()));
        verify(userAuthFalseCache, times(1)).saveUserInCache(userAuthFalseDtoOne);
        verify(userAuthFalseCache, times(1)).isExistUserInCache(chatId);
        verify(userAuthFalseCache, times(1)).getAttemptsAuthFromCache(chatId);
        verify(userAuthFalseCache, times(1)).increaseAttemptAuthToCache(chatId, attemptsAuthUser);
        verify(userService, times(1)).setAuthorizationTrue(chatId);
        verify(userAuthTrueCache, times(1)).saveUserInCache(userAuthTrueDtoOne);
        verify(userAuthFalseCache, times(1)).deleteUserFromAuthCache(chatId);
        verify(senderBotClient, times(1)).sendAnswerMessage(expectedMessage);
    }
}