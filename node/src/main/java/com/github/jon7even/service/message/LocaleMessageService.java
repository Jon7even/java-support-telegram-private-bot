package com.github.jon7even.service.message;

public interface LocaleMessageService {
    String getMessage(String message);

    String getMessage(String message, Object... args);
}
