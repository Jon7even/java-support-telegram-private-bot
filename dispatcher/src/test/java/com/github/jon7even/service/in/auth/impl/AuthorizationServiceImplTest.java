package com.github.jon7even.service.in.auth.impl;

import com.github.jon7even.configuration.SecurityConfig;
import com.github.jon7even.controller.out.SenderBotClient;
import com.github.jon7even.mapper.UserMapper;
import com.github.jon7even.mapper.UserMapperImpl;
import com.github.jon7even.service.in.UserService;
import com.github.jon7even.service.in.auth.cache.UserAuthFalseCache;
import com.github.jon7even.service.in.auth.cache.UserAuthTrueCache;
import com.github.jon7even.setup.TestDataFactory;
import com.github.jon7even.utils.MessageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import static com.github.jon7even.telegram.constants.DefaultBaseMessagesToSend.USER_AUTH_TRUE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
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
public class AuthorizationServiceImplTest extends TestDataFactory {

    @InjectMocks
    private AuthorizationServiceImpl authorizationService;

    @Mock
    private Update update;

    @Mock
    private UserService userService;

    @Mock
    private SenderBotClient senderBotClient;

    @Mock
    private UserAuthFalseCache userAuthFalseCache;

    @Mock
    private UserAuthTrueCache userAuthTrueCache;

    private final UserMapper userMapper = new UserMapperImpl();

    private final String pass = "testPass";
    private final int maxAttempts = 3;

    private long chatId;
    private Message testMessage;
    private SendMessage expectedMessage;

    @BeforeEach
    public void setUp() {
        initUserDto();
        chatId = userCreateDtoOne.getChatId();
        testMessage = Message.builder()
                .chat(Chat.builder()
                        .id(chatId)
                        .type("private")
                        .firstName(userCreateDtoOne.getFirstName())
                        .lastName(userCreateDtoOne.getLastName())
                        .userName(userCreateDtoOne.getLastName())
                        .build())
                .text(pass)
                .build();
        expectedMessage = MessageUtils.buildAnswerWithText(testMessage.getChatId(), USER_AUTH_TRUE);
        SecurityConfig securityConfig = new SecurityConfig();
        securityConfig.setKeyPass(pass);
        securityConfig.setAttemptsAuth(maxAttempts);
        authorizationService = new AuthorizationServiceImpl(
                userService, userMapper, userAuthFalseCache, userAuthTrueCache, securityConfig, senderBotClient
        );
    }

    @Test
    @DisplayName("Новый пользователь ввел правильный пароль и авторизовался")
    public void processAuthorization_WhenNewUserSetValidPass_ReturnsTrue() {
        boolean expectedResult = true;
        int attemptsAuthUser = 0;

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

    @Test
    @DisplayName("Существующий пользователь первый раз ошибся, но второй раз ввел правильный пароль и авторизовался")
    public void processAuthorization_WhenExistsUserSetValidPassAfterFirstAttempt_ReturnsTrue() {
        boolean expectedResult = true;
        int attemptsAuthUser = 1;

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(testMessage);
        when(userAuthTrueCache.isExistUserInCache(chatId)).thenReturn(false);
        when(userService.isExistUserByChatId(chatId)).thenReturn(true);
        when(userAuthFalseCache.isExistUserInCache(chatId)).thenReturn(true);
        when(userAuthFalseCache.getAttemptsAuthFromCache(chatId)).thenReturn(attemptsAuthUser);
        when(userAuthFalseCache.increaseAttemptAuthToCache(chatId, attemptsAuthUser)).thenReturn(2);
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
        verify(userService, never()).createUser(userMapper.toDtoCreateFromMessage(testMessage.getChat()));
        verify(userAuthFalseCache, never()).saveUserInCache(userAuthFalseDtoOne);
        verify(userAuthFalseCache, times(1)).isExistUserInCache(chatId);
        verify(userAuthFalseCache, times(1)).getAttemptsAuthFromCache(chatId);
        verify(userAuthFalseCache, times(1)).increaseAttemptAuthToCache(chatId, attemptsAuthUser);
        verify(userService, times(1)).setAuthorizationTrue(chatId);
        verify(userAuthTrueCache, times(1)).saveUserInCache(userAuthTrueDtoOne);
        verify(userAuthFalseCache, times(1)).deleteUserFromAuthCache(chatId);
        verify(senderBotClient, times(1)).sendAnswerMessage(expectedMessage);
    }

    @Test
    @DisplayName("Существующий и ранее авторизованный пользователь вводит текст после сброса сессии")
    public void processAuthorization_WhenExistsUserIsAuthButAppWasRestartedAndSessionIsClean_ReturnsFalse() {
        boolean expectedResult = false;
        int attemptsAuthUser = 0;
        testMessage.setText("blablabla");
        SendMessage warnMessageAfterRestartApp = MessageUtils.buildAnswerWithText(
                chatId, "Ваша сессия истекла, пожалуйста введите пароль для новой авторизации"
        );

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(testMessage);
        when(userService.isExistUserByChatId(chatId)).thenReturn(true);
        when(userAuthTrueCache.isExistUserInCache(chatId)).thenReturn(false);
        when(userService.updateUser(userMapper.toDtoUpdateFromMessage(testMessage.getChat())))
                .thenReturn(userAuthFalseDtoOne);
        doNothing().when(userAuthFalseCache).saveUserInCache(userAuthFalseDtoOne);
        doNothing().when(senderBotClient).sendAnswerMessage(warnMessageAfterRestartApp);

        boolean resultAuth = authorizationService.processAuthorization(update);

        assertThat(resultAuth)
                .isEqualTo(expectedResult);

        verify(update, times(1)).hasMessage();
        verify(update, times(1)).getMessage();
        verify(userService, times(1)).isExistUserByChatId(chatId);
        verify(userAuthTrueCache, times(1)).isExistUserInCache(chatId);
        verify(userService, times(1)).updateUser(userMapper.toDtoUpdateFromMessage(testMessage.getChat()));
        verify(userService, never()).createUser(userMapper.toDtoCreateFromMessage(testMessage.getChat()));
        verify(userAuthFalseCache, times(1)).saveUserInCache(userAuthFalseDtoOne);
        verify(senderBotClient, times(1)).sendAnswerMessage(warnMessageAfterRestartApp);
        verify(userAuthFalseCache, times(1)).isExistUserInCache(chatId);
        verify(userAuthFalseCache, times(1)).getAttemptsAuthFromCache(chatId);
        verify(userAuthFalseCache, times(1)).increaseAttemptAuthToCache(chatId, attemptsAuthUser);
        verify(userService, never()).setAuthorizationTrue(chatId);
        verify(userAuthTrueCache, never()).saveUserInCache(userAuthTrueDtoOne);
        verify(userAuthFalseCache, never()).deleteUserFromAuthCache(chatId);
    }

    @Test
    @DisplayName("Существующий пользователь неправильно ввёл пароль и попал в бан-лист")
    public void processAuthorization_WhenExistsUserSetNotValidPassAfterAllAttempts_ReturnsFalseAndWasBanned() {
        boolean expectedResult = false;
        int attemptsAuthUser = maxAttempts - 1;

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(testMessage);
        when(userAuthTrueCache.isExistUserInCache(chatId)).thenReturn(false);
        when(userService.isExistUserByChatId(chatId)).thenReturn(true);
        when(userAuthFalseCache.isExistUserInCache(chatId)).thenReturn(true);
        when(userAuthFalseCache.getAttemptsAuthFromCache(chatId)).thenReturn(attemptsAuthUser);
        when(userAuthFalseCache.increaseAttemptAuthToCache(chatId, attemptsAuthUser)).thenReturn(maxAttempts);

        boolean resultAuth = authorizationService.processAuthorization(update);

        assertThat(resultAuth)
                .isEqualTo(expectedResult);

        verify(update, times(1)).hasMessage();
        verify(update, times(1)).getMessage();
        verify(userAuthTrueCache, times(1)).isExistUserInCache(chatId);
        verify(userService, times(1)).isExistUserByChatId(chatId);
        verify(userService, never()).createUser(userMapper.toDtoCreateFromMessage(testMessage.getChat()));
        verify(userAuthFalseCache, never()).saveUserInCache(userAuthFalseDtoOne);
        verify(userAuthFalseCache, times(1)).isExistUserInCache(chatId);
        verify(userAuthFalseCache, times(1)).getAttemptsAuthFromCache(chatId);
        verify(userAuthFalseCache, times(1)).increaseAttemptAuthToCache(chatId, attemptsAuthUser);
        verify(userService, never()).setAuthorizationTrue(chatId);
        verify(userAuthTrueCache, never()).saveUserInCache(userAuthTrueDtoOne);
        verify(userAuthFalseCache, never()).deleteUserFromAuthCache(chatId);
        verify(senderBotClient, never()).sendAnswerMessage(expectedMessage);
    }

    @Test
    @DisplayName("Текстовое сообщение от авторизованного пользователя")
    public void processAuthorization_WhenUserIsAuthAndSendText_ReturnsTrue() {
        boolean expectedResult = true;
        int attemptsAuthUser = 0;
        testMessage.setText("/command");

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(testMessage);
        when(userAuthTrueCache.isExistUserInCache(chatId)).thenReturn(true);

        boolean resultAuth = authorizationService.processAuthorization(update);

        assertThat(resultAuth)
                .isEqualTo(expectedResult);

        verify(update, times(1)).hasMessage();
        verify(update, times(1)).getMessage();
        verify(userAuthTrueCache, times(1)).isExistUserInCache(chatId);
        verify(userService, never()).isExistUserByChatId(chatId);
        verify(userService, never()).createUser(userMapper.toDtoCreateFromMessage(testMessage.getChat()));
        verify(userAuthFalseCache, never()).saveUserInCache(userAuthFalseDtoOne);
        verify(userAuthFalseCache, never()).isExistUserInCache(chatId);
        verify(userAuthFalseCache, never()).getAttemptsAuthFromCache(chatId);
        verify(userAuthFalseCache, never()).increaseAttemptAuthToCache(chatId, attemptsAuthUser);
        verify(userService, never()).setAuthorizationTrue(chatId);
        verify(userAuthTrueCache, never()).saveUserInCache(userAuthTrueDtoOne);
        verify(userAuthFalseCache, never()).deleteUserFromAuthCache(chatId);
        verify(senderBotClient, never()).sendAnswerMessage(any(SendMessage.class));
    }

    @Test
    @DisplayName("Нажатие на клавиатуру от авторизованного пользователя")
    public void processAuthorization_WhenUserIsAuthAndCallbackQuery_ReturnsTrue() {
        boolean expectedResult = true;
        testMessage.setText("/command");
        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setMessage(testMessage);

        when(update.hasMessage()).thenReturn(false);
        when(update.hasCallbackQuery()).thenReturn(true);
        when(update.getCallbackQuery()).thenReturn(callbackQuery);
        when(userAuthTrueCache.isExistUserInCache(chatId)).thenReturn(true);

        boolean resultAuth = authorizationService.processAuthorization(update);

        assertThat(resultAuth)
                .isEqualTo(expectedResult);

        verify(update, times(1)).hasMessage();
        verify(update, times(1)).hasCallbackQuery();
        verify(update, times(1)).getCallbackQuery();
        verify(userAuthTrueCache, times(1)).isExistUserInCache(chatId);
    }

    @Test
    @DisplayName("Нажатие на клавиатуру от неавторизованного пользователя")
    public void processAuthorization_WhenUserIsNotAuthAndCallbackQuery_ReturnsFalse() {
        boolean expectedResult = false;
        testMessage.setText("/command");
        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setMessage(testMessage);

        when(update.hasMessage()).thenReturn(false);
        when(update.hasCallbackQuery()).thenReturn(true);
        when(update.getCallbackQuery()).thenReturn(callbackQuery);
        when(userAuthTrueCache.isExistUserInCache(chatId)).thenReturn(false);

        boolean resultAuth = authorizationService.processAuthorization(update);

        assertThat(resultAuth)
                .isEqualTo(expectedResult);

        verify(update, times(1)).hasMessage();
        verify(update, times(1)).hasCallbackQuery();
        verify(update, times(1)).getCallbackQuery();
        verify(userAuthTrueCache, times(1)).isExistUserInCache(chatId);
    }

    @Test
    @DisplayName("Пользователь прислал контент, который еще не поддерживается ботом")
    public void processAuthorization_WhenUserUsedOtherContent_ReturnsFalse() {
        boolean expectedResult = false;

        when(update.hasMessage()).thenReturn(false);
        when(update.hasCallbackQuery()).thenReturn(false);

        boolean resultAuth = authorizationService.processAuthorization(update);

        assertThat(resultAuth)
                .isEqualTo(expectedResult);

        verify(update, times(1)).hasMessage();
        verify(update, times(1)).hasCallbackQuery();
    }
}