package com.github.jon7even.service.in;

import com.github.jon7even.dto.UserAuthFalseDto;
import com.github.jon7even.dto.UserAuthTrueDto;
import com.github.jon7even.dto.UserCreateDto;
import com.github.jon7even.entity.user.UserEntity;
import com.github.jon7even.exception.AccessDeniedException;
import com.github.jon7even.exception.AlreadyExistException;
import com.github.jon7even.mapper.UserMapper;
import com.github.jon7even.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Реализация сервиса взаимодействия с пользователями
 *
 * @author Jon7even
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserAuthFalseDto createUser(UserCreateDto userCreateDto) {
        if (!isExistUserByChatId(userCreateDto.getChatId())) {
            log.trace("Начинаю сохранять нового пользователя, данные для сохранения [userCreateDto={}]", userCreateDto);
            UserEntity userForSaveInRepository = userMapper.toEntityFromCreateDto(userCreateDto, LocalDateTime.now());
            UserEntity createdUserFromRepository = userRepository.save(userForSaveInRepository);
            log.trace("Новый пользователь сохранен в БД [userEntity]={}", createdUserFromRepository);
            return userMapper.toAuthFalseDtoFromEntity(createdUserFromRepository);
        } else {
            throw new AlreadyExistException(String.format("userCreateDto=%s", userCreateDto));
        }
    }

    @Override
    public boolean isExistUserByChatId(Long chatId) {
        log.trace("Начинаю проверять существует ли пользователь с [chatId={}] в БД", chatId);
        return userRepository.existsByChatId(chatId);
    }

    @Override
    public UserAuthTrueDto setAuthorizationTrue(Long chatId) {
        log.trace("Начинаю выставлять авторизацию true для пользователя с [chatId={}]", chatId);
        Optional<UserEntity> userForSetAuthorizationTrue = userRepository.findByChatId(chatId);

        if (userForSetAuthorizationTrue.isEmpty() || !userForSetAuthorizationTrue.get().getAuthorization()) {
            UserEntity userForUpdate = userForSetAuthorizationTrue.get();
            userForUpdate.setAuthorization(true);
            userForUpdate.setUpdatedOn(LocalDateTime.now());
            UserEntity updatedUserFromRepository = userRepository.save(userForUpdate);
            log.trace("Пользователь обновлен в БД [userEntity]={}", updatedUserFromRepository);
            return userMapper.toAuthTrueDtoFromEntity(updatedUserFromRepository);
        } else {
            throw new AccessDeniedException(String.format("Set Authorization true for User with [chatId=%s]", chatId));
        }
    }
}