package com.github.jon7even.service;

import com.github.jon7even.cache.UserAuthCache;
import com.github.jon7even.configuration.SecurityConfig;
import com.github.jon7even.model.user.UserEntity;
import com.github.jon7even.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;
    private final UserAuthCache userAuthCache;

    @Override
    public boolean processAuthorization(Update update) {
        UserEntity userFromBD = registerUser(update.getMessage());

        if (userFromBD.getAuthorization()) {
            log.debug("Пользователь авторизован");
            return true;
        } else {
            log.warn("Пользователь user={} еще не авторизован", userFromBD);
            return processInputPassword(userFromBD, update);
        }
    }

    private boolean processInputPassword(UserEntity userFromBD, Update update) {
        if (isUserBanned(userFromBD.getId())) {
            String resultMessage = update.getMessage().getText();

            if (resultMessage.equals(securityConfig.getKeyPass())) {
                userFromBD.setAuthorization(true);
                log.warn("Пользователь ввел правильный пароль, сохраняем в базу user={}", userFromBD);
                userRepository.save(userFromBD);
                log.trace("Пользователь user={} прошел авторизацию", userFromBD);
                userAuthCache.deleteUserFromAuthCache(userFromBD.getId());
                return true;
            } else {
                log.warn("Пользователь user={} пытается подобрать пароль", userFromBD);
                return false;
            }
        } else {
            log.warn("Пользователь user={} ввел count={} раз пароль неправильно и был заблокирован",
                    userFromBD, securityConfig.getAttemptsAuth());
            return false;
        }
    }

    private UserEntity registerUser(Message message) {
        var chatId = message.getChatId();
        UserEntity user;

        if (userRepository.existsByChatId(chatId)) {
            user = userRepository.findByChatId(chatId);
            log.debug("Пользователь c tgId={} и userId={} уже есть в системе", chatId, user.getId());
        } else {
            log.info("Начинаю регистрацию нового пользователя");
            log.debug("Начинаю регистрацию нового пользователя с tgId={}", chatId);

            var chat = message.getChat();
            UserEntity userForSave = UserEntity.builder()
                    .chatId(chatId)
                    .firstName(chat.getFirstName())
                    .lastName(chat.getLastName())
                    .userName(chat.getUserName())
                    .registeredOn(LocalDateTime.now())
                    .authorization(false)
                    .build();
            log.debug("Новый пользователь собран user={}", userForSave);

            user = userRepository.save(userForSave);
            log.debug("Пользователь успешно сохранен в БД user={}", user);

            userAuthCache.setAttemptAuthForUserCache(chatId, 0);
        }
        return user;
    }

    private boolean isUserBanned(Long userId) {
        int currentAttemptsAuthUser = userAuthCache.getAttemptsAuthForUserCache(userId);
        currentAttemptsAuthUser++;
        userAuthCache.setAttemptAuthForUserCache(userId, currentAttemptsAuthUser);
        return securityConfig.getAttemptsAuth() >= currentAttemptsAuthUser;
    }

}
