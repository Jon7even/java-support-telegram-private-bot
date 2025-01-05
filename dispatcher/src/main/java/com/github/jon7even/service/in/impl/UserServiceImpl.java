package com.github.jon7even.service.in.impl;

import com.github.jon7even.dto.UserAuthFalseDto;
import com.github.jon7even.dto.UserAuthTrueDto;
import com.github.jon7even.dto.UserCreateDto;
import com.github.jon7even.dto.UserUpdateDto;
import com.github.jon7even.entity.user.UserEntity;
import com.github.jon7even.exception.AccessDeniedException;
import com.github.jon7even.exception.AlreadyExistException;
import com.github.jon7even.exception.NotFoundException;
import com.github.jon7even.mapper.UserMapper;
import com.github.jon7even.repository.UserRepository;
import com.github.jon7even.service.in.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса взаимодействия с пользователями
 *
 * @author Jon7even
 * @version 2.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = {AlreadyExistException.class})
    public UserAuthFalseDto createUser(UserCreateDto userCreateDto) throws AlreadyExistException {
        checkUserExistenceByChatId(userCreateDto.getChatId());

        log.debug("Начинаю сохранять нового пользователя, данные для сохранения [userCreateDto={}]", userCreateDto);
        UserEntity userForSaveInRepository = userMapper.toEntityFromCreateDto(userCreateDto);

        UserEntity createdUserFromRepository = userRepository.save(userForSaveInRepository);
        log.trace("Новый пользователь сохранен в БД [userEntity]={}", createdUserFromRepository);
        return userMapper.toAuthFalseDtoFromEntity(createdUserFromRepository);
    }

    @Override
    @Transactional(rollbackFor = {NotFoundException.class})
    public UserAuthFalseDto updateUser(UserUpdateDto userUpdateDto) throws NotFoundException {
        log.trace("Начинаю обновлять существующего пользователя, DTO для обновления [userUpdateDto={}]", userUpdateDto);
        UserEntity userFromRepository = getUserByChatId(userUpdateDto.getChatId());

        log.debug("Объединяю данные в сущности");
        userMapper.updateUserEntityFromDtoUpdate(userFromRepository, userUpdateDto, false);
        log.debug("Сохраняю обновленные данные пользователя [UserEntity={}]", userFromRepository);

        UserEntity userUpdatedFromRepository = userRepository.save(userFromRepository);
        log.trace("Пользователь обновлен [UserEntity={}]", userUpdatedFromRepository);
        return userMapper.toAuthFalseDtoFromEntity(userUpdatedFromRepository);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistUserByChatId(Long chatId) {
        log.trace("Начинаю проверять существует ли пользователь с [chatId={}] в БД", chatId);
        return userRepository.existsByChatId(chatId);
    }

    @Override
    @Transactional(rollbackFor = {NotFoundException.class})
    public UserAuthTrueDto setAuthorizationTrue(Long chatId) throws NotFoundException, AccessDeniedException {
        log.debug("Начинаю выставлять авторизацию true для пользователя с [chatId={}]", chatId);
        UserEntity userForSetAuthorizationTrue = getUserByChatId(chatId);

        log.trace("На текущий момент поле авторизации [authorization={}] у пользователя c [chatId={}]",
                userForSetAuthorizationTrue.getAuthorization(), chatId
        );

        userMapper.updateUserEntitySetAuthorizationIsTrue(userForSetAuthorizationTrue);

        UserEntity updatedUserFromRepository = userRepository.save(userForSetAuthorizationTrue);
        log.trace("Пользователь обновлен в БД и прошел авторизацию [userEntity]={}", updatedUserFromRepository);

        return userMapper.toAuthTrueDtoFromEntity(updatedUserFromRepository);
    }

    private UserEntity getUserByChatId(Long chatId) {
        log.debug("Начинаю искать пользователя с [chatId={}]", chatId);
        return userRepository.findByChatId(chatId)
                .orElseThrow(() -> new NotFoundException(String.format("User with [chatId=%d]", chatId)));
    }

    private void checkUserExistenceByChatId(Long chatId) {
        if (isExistUserByChatId(chatId)) {
            throw new AlreadyExistException(String.format("User with [chatId=%d]", chatId));
        }
    }
}