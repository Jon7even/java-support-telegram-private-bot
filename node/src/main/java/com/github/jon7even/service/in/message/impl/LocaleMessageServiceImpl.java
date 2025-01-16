package com.github.jon7even.service.in.message.impl;

import com.github.jon7even.service.in.message.LocaleMessageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Реализация сервиса языковой локализации {@link LocaleMessageService}.
 *
 * @author Jon7even
 * @version 2.0
 */
@Service
@RequiredArgsConstructor
public class LocaleMessageServiceImpl implements LocaleMessageService {

    @Value("${localeTag}")
    private String localeTag;

    private Locale locale;

    private final MessageSource messageSource;

    @PostConstruct
    public void initLocale() {
        this.locale = Locale.forLanguageTag(localeTag);
    }

    @Override
    public String getMessage(String replyText) {
        return messageSource.getMessage(replyText, null, locale);
    }

    @Override
    public String getMessage(String replyText, Object... args) {
        return messageSource.getMessage(replyText, args, locale);
    }
}