package com.github.jon7even.service;

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
        String resultMessage = update.getMessage().getText();

        if (resultMessage.equals(securityConfig.getKeyPass())) {
            userFromBD.setAuthorization(true);
            log.debug("Пользователь ввел пароль, сохраняем в базу user={}", userFromBD);
            userRepository.save(userFromBD);
            log.trace("Пользователь user={} прошел авторизацию", userFromBD);
            return true;
        } else {
            log.warn("Пользователь user={} пытается подобрать пароль", userFromBD);
            return false;
        }
    }

    private UserEntity registerUser(Message message) {
        var chatId = message.getChatId();
        UserEntity createdUser;

        if (userRepository.existsByChatId(chatId)) {
            createdUser = userRepository.findByChatId(chatId);
            log.info("Это наш старожил, пользователь уже есть в системе.");
            log.debug("Это наш старожил, пользователь уже есть в системе с tgId={}", chatId);
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

            createdUser = userRepository.save(userForSave);
            log.debug("Пользователь успешно сохранен в БД user={}", createdUser);
        }
        return createdUser;
    }

}
