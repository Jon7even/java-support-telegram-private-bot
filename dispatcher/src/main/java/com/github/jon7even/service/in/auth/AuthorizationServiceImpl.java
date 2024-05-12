package com.github.jon7even.service.in.auth;

import com.github.jon7even.configuration.SecurityConfig;
import com.github.jon7even.dto.UserShortDto;
import com.github.jon7even.entities.user.UserEntity;
import com.github.jon7even.mapper.UserMapper;
import com.github.jon7even.repository.UserRepository;
import com.github.jon7even.service.in.auth.cache.UserAuthCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Реализация сервиса авторизации пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;
    private final UserAuthCache userAuthCache;

    @Override
    public boolean processAuthorization(UserShortDto userShortDto) {
        log.debug("На авторизацию пришел пользователь: userDto={}", userShortDto);
        UserEntity userFromBD = registerOrGetUser(userShortDto);

        if (userFromBD.getAuthorization()) {
            log.debug("Пользователь авторизован");
            return true;
        } else {
            log.warn("Пользователь user={} еще не авторизован", userFromBD);
            return processInputPassword(userFromBD, userShortDto);
        }
    }

    @Override
    public boolean processAuthorizationForCallBack(Long userId) {
        UserEntity userFromBD = getUserByChatId(userId);

        if (userFromBD.getAuthorization()) {
            log.debug("Пользователь авторизован");
            log.debug("Пользователь c tgId={} и userId={} уже есть в системе",
                    userFromBD.getChatId(), userFromBD.getId());
            return true;
        } else {
            log.error("Пользователь user={} странная попытка авторизоваться через CallbackQuery", userFromBD);
            return false;
        }
    }

    private boolean processInputPassword(UserEntity userFromBD, UserShortDto userShortDto) {
        if (isUserBanned(userFromBD.getId())) {
            if (userShortDto.getTextMessage().equals(securityConfig.getKeyPass())) {
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
            log.warn("Пользователь user={} ввел attemptsAuth={} раз пароль неправильно и был заблокирован",
                    userFromBD, securityConfig.getAttemptsAuth());
            return false;
        }
    }

    private UserEntity registerOrGetUser(UserShortDto userShortDto) {
        UserEntity user;
        Long chatId = userShortDto.getChatId();

        if (userRepository.existsByChatId(chatId)) {
            user = getUserByChatId(chatId);
            log.debug("Пользователь c tgId={} и userId={} уже есть в системе", chatId, user.getId());
        } else {
            log.info("Начинаю регистрацию нового пользователя");
            user = registerNewUser(userShortDto, chatId);
            userAuthCache.setAttemptAuthForUserCache(chatId, 0);
        }
        return user;
    }

    private UserEntity registerNewUser(UserShortDto userShortDto, Long chatId) {
        log.debug("Начинаю регистрацию нового пользователя с tgId={}", chatId);
        UserEntity userForSave = UserMapper.INSTANCE.toEntityFromShortDto(userShortDto, LocalDateTime.now());
        log.debug("Новый пользователь собран user={}", userForSave);

        UserEntity userAfterSave = userRepository.save(userForSave);
        log.debug("Пользователь успешно сохранен в БД user={}", userAfterSave);
        return userAfterSave;
    }

    private boolean isUserBanned(Long userId) {
        int currentAttemptsAuthUser = userAuthCache.getAttemptsAuthForUserCache(userId);
        currentAttemptsAuthUser++;
        userAuthCache.setAttemptAuthForUserCache(userId, currentAttemptsAuthUser);
        return securityConfig.getAttemptsAuth() >= currentAttemptsAuthUser;
    }

    private UserEntity getUserByChatId(Long chatId) {
        log.debug("Получаю пользователя с chatId={} из базы", chatId);
        return userRepository.findByChatId(chatId);
    }
}
