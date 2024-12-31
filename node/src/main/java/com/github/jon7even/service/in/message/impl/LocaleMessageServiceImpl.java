package com.github.jon7even.service.in.message.impl;

import com.github.jon7even.service.in.message.LocaleMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Реализация сервиса языковой локализации {@link LocaleMessageService}
 *
 * @author Jon7even
 * @version 2.0
 */
@Service
public class LocaleMessageServiceImpl implements LocaleMessageService {

    private final Locale locale;

    private final MessageSource messageSource;

    public LocaleMessageServiceImpl(@Value("${localeTag}") String localeTag, MessageSource messageSource) {
        this.locale = Locale.forLanguageTag(localeTag);
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String message) {
        return messageSource.getMessage(message, null, locale);
    }

    @Override
    public String getMessage(String message, Object... args) {
        return messageSource.getMessage(message, args, locale);
    }
}