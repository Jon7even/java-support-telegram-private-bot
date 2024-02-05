package com.github.jon7even.service;

public interface LocaleMessageService {
    String getMessage(String message);

    String getMessage(String message, Object... args);
}
