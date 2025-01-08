package com.github.jon7even.service.in.handle.impl;

import com.github.jon7even.cache.UserDataCache;
import com.github.jon7even.service.in.handle.HandlerService;
import com.github.jon7even.service.in.message.ReplyMessageService;
import com.github.jon7even.service.out.producer.SenderMessageService;
import com.github.jon7even.telegram.BotState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Реализация сервиса обработки данных от пользователей {@link HandlerService}
 *
 * @author Jon7even
 * @version 2.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HandlerServiceImpl implements HandlerService {

    private final UserDataCache userDataCache;

    private final ReplyMessageService replyMessageService;

    private final SenderMessageService senderMessageService;

    @Override
    public void processTextMessage(Update update) {
        String resultMessage = update.getMessage().getText();
        Long chaId = update.getMessage().getChatId();

        switch (resultMessage) {
            case "/gifts":
                senderMessageService.sendText(chaId, replyMessageService.getReplyText("reply.nonSupportedYet"));
                break;
            case "/ask":
                senderMessageService.sendText(chaId,
                        "Бу испугался! Не бойса! Данная команда еще находится в разработке"
                );
                break;
            default:
                senderMessageService.sendText(chaId, replyMessageService.getReplyText("reply.nonSupport"));
                userDataCache.setBotStateForCacheUser(chaId, BotState.MAIN_HELP);
                log.warn("Эту команду мы еще не поддерживаем. Команда пользователя: " + resultMessage);
        }
    }

    @Override
    public void processCallbackQuery(Update update) {
        String queryCallbackQuery = update.getCallbackQuery().getData();
        Long chaId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        log.debug("Пользователь {} нажал на клавиатуру в сообщении {} и передает: {}",
                chaId, queryCallbackQuery, messageId);

        switch (queryCallbackQuery) {
            case "/anyCallback":
                senderMessageService.sendEditText(
                        chaId, replyMessageService.getReplyText("reply.callBackAddNewCompany"), messageId
                );
                break;
            default:
                senderMessageService.sendText(chaId, replyMessageService.getReplyText("reply.nonSupport"));
                log.warn("Эту команду мы еще не поддерживаем. Команда пользователя: " + queryCallbackQuery);
        }
    }
}