package com.github.jon7even.service.in.handle.impl;

import com.github.jon7even.service.in.handle.UserHandlerService;
import com.github.jon7even.service.in.message.ReplyMessageService;
import com.github.jon7even.service.in.status.UserStatusService;
import com.github.jon7even.service.out.producer.SenderMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.jon7even.telegram.constants.DefaultMessageLogError.ERROR_COMMAND_NOT_SUPPORT;

/**
 * Реализация обработчика {@link UserHandlerService} для команд клавиатуры.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Используется для обработки основных команд клавиатуры.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StandardCallbackHandlerImpl implements UserHandlerService {

    private final ReplyMessageService replyMessageService;

    private final SenderMessageService senderMessageService;

    private final UserStatusService userStatusService;

    @Override
    public void handle(Update update) {
        String queryCallbackQuery = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        log.info("Пользователь {} нажал на клавиатуру в сообщении {} и передает: {}",
                chatId, queryCallbackQuery, messageId);

        switch (queryCallbackQuery) {
            case "/anyCallback" -> senderMessageService.sendEditText(
                    chatId, replyMessageService.getReplyText("reply.callBack.AddNewCompany"), messageId
            );
            default -> {
                senderMessageService.sendText(chatId, replyMessageService.getReplyText("reply.nonSupport"));
                log.trace(ERROR_COMMAND_NOT_SUPPORT + "нажатие на клавиатуру [{}]", queryCallbackQuery);
            }
        }
    }
}