package com.github.jon7even.service.in.status.impl;

import com.github.jon7even.entity.UserStatusEntity;
import com.github.jon7even.mapper.UserStatusMapper;
import com.github.jon7even.repository.UserStatusRepository;
import com.github.jon7even.service.in.status.UserStatusService;
import com.github.jon7even.telegram.BotState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса отвечающего за статус бота для пользователей {@link UserStatusService}.
 *
 * @author Jon7even
 * @version 2.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserStatusServiceImpl implements UserStatusService {

    private final UserStatusRepository userStatusRepository;

    private final UserStatusMapper userStatusMapper;

    @Override
    @Transactional
    public void setBotStateForUser(Long chatId, BotState botState) {
        log.debug("Присваиваю пользователю c [chatId={}] статус [botState={}]", chatId, botState);
        UserStatusEntity userStatusForSave = userStatusMapper.toUserStatusEntityFromChatIdAndStatus(chatId, botState);
        userStatusRepository.save(userStatusForSave);
        log.debug("Статус [botState={}] присвоен пользователю c [chatId={}]", botState, chatId);
    }

    @Override
    @Transactional(readOnly = true)
    public BotState getBotStateForUser(Long chatId) {
        log.debug("Получаю статус пользователя c [chatId={}]", chatId);
        return userStatusRepository.findById(chatId)
                .map(UserStatusEntity::getStatus)
                .orElseGet(() -> {
                    log.warn("Статус у пользователя c [chatId={}] не найден, присваиваю стандартный", chatId);
                    UserStatusEntity defaultStatus = userStatusMapper.toDefaultUserStatusEntityFromChatId(chatId);
                    userStatusRepository.save(defaultStatus);
                    return BotState.MAIN_START;
                });
    }
}