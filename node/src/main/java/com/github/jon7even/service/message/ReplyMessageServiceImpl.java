package com.github.jon7even.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
