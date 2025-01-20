package com.github.jon7even.service.in.handle.impl;

import com.github.jon7even.service.in.handle.HandlerService;
import com.github.jon7even.service.in.handle.UserHandlerService;
import com.github.jon7even.service.in.handle.factory.UserHandlerFactory;
import com.github.jon7even.service.in.message.ReplyMessageService;
import com.github.jon7even.service.in.status.UserStatusService;
import com.github.jon7even.service.out.producer.SenderMessageService;
import com.github.jon7even.telegram.BotState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Реализация сервиса обработки данных от пользователей {@link HandlerService}.
 *
 * @author Jon7even
 * @version 2.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandlerServiceImpl implements HandlerService {

    private final ReplyMessageService replyMessageService;

    private final SenderMessageService senderMessageService;

    private final UserStatusService userStatusService;

    private final UserHandlerFactory userHandlerFactory;

    @Override
    public void processTextMessage(Update update) {
        Long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        log.info("Пользователь c [chatId={}] прислал текст [text={}], начинаем обрабатывать...", chatId, text);
        handleQuery(update);
    }

    @Override
    public void processCallbackQuery(Update update) {
        String queryCallbackQuery = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        log.info("Пользователь c [chatId={}] нажал на клавиатуру в сообщении [messageId={}] и передает: [{}]",
                chatId, queryCallbackQuery, messageId);
        handleQuery(update);
    }

    private void handleQuery(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        log.debug("Выявляю текущий статус пользователя c [chatId={}]", chatId);
        BotState currentBotState = userStatusService.getBotStateForUser(chatId);
        log.info("Текущий статус пользователя c [chatId={}] является [BotState={}]", chatId, currentBotState);

        log.debug("Определяю обработчик для пользователя c [chatId={}]", chatId);
        UserHandlerService userHandlerService = userHandlerFactory.getHandlerForUser(currentBotState);
        log.info("Пользователю c [chatId={}] выбран обработчик {}", chatId, userHandlerService.getClass().getName());

        userHandlerService.handle(update);
    }
}