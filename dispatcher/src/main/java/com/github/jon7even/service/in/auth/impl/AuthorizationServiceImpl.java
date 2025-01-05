package com.github.jon7even.service.in.auth.impl;

import com.github.jon7even.configuration.SecurityConfig;
import com.github.jon7even.controller.out.SenderBotClient;
import com.github.jon7even.dto.UserAuthFalseDto;
import com.github.jon7even.dto.UserAuthTrueDto;
import com.github.jon7even.dto.UserCreateDto;
import com.github.jon7even.dto.UserUpdateDto;
import com.github.jon7even.exception.AccessDeniedException;
import com.github.jon7even.exception.AlreadyExistException;
import com.github.jon7even.exception.NotFoundException;
import com.github.jon7even.mapper.UserMapper;
import com.github.jon7even.service.in.UserService;
import com.github.jon7even.service.in.auth.AuthorizationService;
import com.github.jon7even.service.in.auth.cache.UserAuthFalseCache;
import com.github.jon7even.service.in.auth.cache.UserAuthTrueCache;
import com.github.jon7even.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import static com.github.jon7even.telegram.constants.DefaultBaseMessagesToSend.USER_AUTH_TRUE;

/**
 * Реализация сервиса авторизации пользователей
 *
 * @author Jon7even
 * @version 2.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserService userService;

    private final UserMapper userMapper;

    private final UserAuthFalseCache userAuthFalseCache;

    private final UserAuthTrueCache userAuthTrueCache;

    private final SecurityConfig securityConfig;

    private final SenderBotClient senderBotClient;

    @Override
    public boolean processAuthorization(Update update) {
        if (update.hasMessage()) {
            var message = update.getMessage();
            return checkAuthorizationFromMessage(message);
        } else if (update.hasCallbackQuery()) {
            var callbackQuery = update.getCallbackQuery();
            return checkAuthorizationFromCallBack(callbackQuery);
        } else {
            return false;
        }
    }

    private boolean checkAuthorizationFromMessage(Message message) {
        if (isUserAuthorized(message)) {
            return true;
        } else {
            if (!isUserBanned(message)) {
                try {
                    processInputPassword(message);
                    return true;
                } catch (AccessDeniedException exception) {
                    return false;
                }
            }
            return false;
        }
    }

    private boolean checkAuthorizationFromCallBack(CallbackQuery callbackQuery) {
        var chatIdUser = callbackQuery.getMessage().getChatId();

        if (userAuthTrueCache.isExistUserInCache(chatIdUser)) {
            log.debug("Пользователь с [chatId={}] авторизован", chatIdUser);
            return true;
        } else {
            var callBackData = callbackQuery.getData();
            log.error("Пользователь с [chatId={}] подозрительная попытка авторизации через [callbackData={}]",
                    chatIdUser, callBackData);
            return false;
        }
    }

    private boolean isUserAuthorized(Message message) {
        var chatIdUser = message.getChatId();

        if (userAuthTrueCache.isExistUserInCache(chatIdUser)) {
            log.debug("Пользователь с [chatId={}] авторизован", chatIdUser);
            return true;
        } else {
            if (userService.isExistUserByChatId(chatIdUser)) {
                log.trace("Пользователь с [chatId={}] есть в БД, но еще не авторизован", chatIdUser);
            } else {
                log.trace("Пользователя с [chatId={}] нет в БД, требуется регистрация", chatIdUser);
                try {
                    registerUser(message);
                } catch (AlreadyExistException exception) {
                    log.error("Срочно проверьте логи, произошла коллизия [exception={}]", exception.getErrorMessage());
                }
            }
            return false;
        }
    }

    private void processInputPassword(Message message) {
        var chatIdUser = message.getChatId();
        var textInChatByUser = message.getText();

        log.trace("Начинаем проверять правильно ли пользователь с [chatId={}] ввел пароль. Он написал [text={}]",
                chatIdUser, textInChatByUser);

        if (textInChatByUser.equals(securityConfig.getKeyPass())) {
            log.trace("Пользователь с [chatId={}] ввел правильный пароль, сохраняем в БД", chatIdUser);
            UserAuthTrueDto userForSaveInCache = userService.setAuthorizationTrue(chatIdUser);
            log.trace("Пользователь с [chatId={}] ввел правильный пароль, сохраняем в кэш", chatIdUser);

            userAuthTrueCache.saveUserInCache(userForSaveInCache);
            log.trace("Пользователь с [chatId={}] ввел правильный пароль, удаляем из кэша для неавторизованных "
                    + "пользователей", chatIdUser);

            userAuthFalseCache.deleteUserFromAuthCache(chatIdUser);
            log.trace("Пользователь с [chatId={}] прошел авторизацию", chatIdUser);

            var sendMessage = MessageUtils.buildAnswerWithText(message.getChatId(), USER_AUTH_TRUE);
            senderBotClient.sendAnswerMessage(sendMessage);
        } else {
            throw new AccessDeniedException(String.format("Authorization user with [chatId=%d]", chatIdUser));
        }
    }

    private boolean isUserBanned(Message message) {
        var chatId = message.getChatId();
        if (!userAuthFalseCache.isExistUserInCache(chatId)) {
            log.warn("Произошел рестарт приложения, сессия у пользователей истекла, требуется обновить данные "
                    + "пользователя с [chatId={}]", chatId);
            try {
                updateUserAfterRestartApp(message);
            } catch (NotFoundException exception) {
                log.error("Проверьте логи приложения, произошла неправильная работа приложения, пользователь "
                        + "с [chatId={}] не существует", chatId);
                return true;
            }
        }

        int currentAttemptsAuthUser = getCountAttemptsAuthForUser(chatId);
        if (securityConfig.getAttemptsAuth() <= currentAttemptsAuthUser) {
            log.warn("Пользователь с [chatId={}] находится в бан-листе ввел пароль [attemptsAuth={}] раз, "
                    + "доступ запрещен", chatId, currentAttemptsAuthUser);
            return true;
        } else {
            log.debug("Пользователя с [chatId={}] в бан-листе не обнаружено", chatId);
            return false;
        }
    }

    private int getCountAttemptsAuthForUser(Long chatId) {
        var attemptsAuthUser = userAuthFalseCache.getAttemptsAuthFromCache(chatId);
        var currentAttemptsAuthUser = userAuthFalseCache.increaseAttemptAuthToCache(chatId, attemptsAuthUser);

        if (currentAttemptsAuthUser > securityConfig.getAttemptsAuth()) {
            log.warn("Пользователь с [chatId={}] пытается подобраться пароль", chatId);
        }
        return currentAttemptsAuthUser;
    }

    private void registerUser(Message message) {
        Long chatId = message.getChatId();
        log.trace("Начинаю регистрировать пользователя с [chatId={}]", chatId);
        UserCreateDto userForSaveInRepository = userMapper.toDtoCreateFromMessage(message.getChat());
        try {
            UserAuthFalseDto userFromRepository = userService.createUser(userForSaveInRepository);
            userAuthFalseCache.saveUserInCache(userFromRepository);
        } catch (AlreadyExistException exception) {
            log.error("Сохранить пользователя не получилось: {}", exception.getErrorMessage());
            var errorMessage = MessageUtils.buildAnswerWithText(chatId, "Произошла ошибка: вы уже в системе");
            senderBotClient.sendAnswerMessage(errorMessage);
        }
    }

    private void updateUserAfterRestartApp(Message message) {
        log.trace("Начинаю обновлять пользователя с [chatId={}]", message.getChatId());
        UserUpdateDto userForUpdate = userMapper.toDtoUpdateFromMessage(message.getChat());
        UserAuthFalseDto updatedUserFromRepository = userService.updateUser(userForUpdate);
        userAuthFalseCache.saveUserInCache(updatedUserFromRepository);
    }
}