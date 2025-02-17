package com.github.jon7even.service.in.handle.impl;

import com.github.jon7even.service.in.handle.UserHandlerService;
import com.github.jon7even.service.in.message.ReplyMessageService;
import com.github.jon7even.service.in.status.UserStatusService;
import com.github.jon7even.service.out.producer.SenderMessageService;
import com.github.jon7even.telegram.BotState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.jon7even.telegram.constants.DefaultMessageLogError.ERROR_COMMAND_NOT_SUPPORT;

/**
 * Реализация обработчика {@link UserHandlerService} для базовых команд.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Используется для обработки основных текстовых команд и сообщений.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StandardTextHandlerImpl implements UserHandlerService {

    private final ReplyMessageService replyMessageService;

    private final SenderMessageService senderMessageService;

    private final UserStatusService userStatusService;

    @Override
    public void handle(Update update) {
        String resultTextFromMessage = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        switch (resultTextFromMessage) {
            case "/gifts" -> {
                senderMessageService.sendText(chatId, replyMessageService.getReplyText("reply.nonSupportedYet"));
                userStatusService.setBotStateForUser(chatId, BotState.MAIN_GIFTS);
            }
            case "/ask" -> {
                senderMessageService.sendText(chatId, replyMessageService.getReplyText("reply.bu"));
                userStatusService.setBotStateForUser(chatId, BotState.MAIN_ASK);
            }
            default -> {
                senderMessageService.sendText(chatId, replyMessageService.getReplyText("reply.nonSupport"));
                userStatusService.setBotStateForUser(chatId, BotState.MAIN_START);
                log.trace(ERROR_COMMAND_NOT_SUPPORT + "текст: [{}]", resultTextFromMessage);
            }
        }
    }
}