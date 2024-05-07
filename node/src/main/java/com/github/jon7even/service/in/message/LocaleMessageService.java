package com.github.jon7even.service.in.message;

public interface LocaleMessageService {
    String getMessage(String message);

    String getMessage(String message, Object... args);
}
