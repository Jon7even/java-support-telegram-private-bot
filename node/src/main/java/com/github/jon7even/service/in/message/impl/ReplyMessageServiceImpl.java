package com.github.jon7even.service.in.message.impl;

import com.github.jon7even.service.in.message.LocaleMessageService;
import com.github.jon7even.service.in.message.ReplyMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса конвертации сообщений {@link ReplyMessageService}
 *
 * @author Jon7even
 * @version 2.0
 */
@Service
@RequiredArgsConstructor
public class ReplyMessageServiceImpl implements ReplyMessageService {

    private final LocaleMessageService localeMessageService;

    @Override
    public String getReplyText(String replyText) {
        return localeMessageService.getMessage(replyText);
    }

    @Override
    public String getReplyText(String replyText, Object... args) {
        return localeMessageService.getMessage(replyText, args);
    }
}