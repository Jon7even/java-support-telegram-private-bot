package com.github.jon7even.service.in.status.impl;

import com.github.jon7even.service.in.status.UserStatusService;
import com.github.jon7even.telegram.BotState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса отвечающего за статус бота для пользователей {@link UserStatusService}
 *
 * @author Jon7even
 * @version 2.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserStatusServiceImpl implements UserStatusService {

    @Override
    public void setBotStateForUser(Long chatId, BotState botState) {
        //TODO
    }

    @Override
    public BotState getBotStateForUser(Long chatId) {
        //TODO
        return null;
    }
}