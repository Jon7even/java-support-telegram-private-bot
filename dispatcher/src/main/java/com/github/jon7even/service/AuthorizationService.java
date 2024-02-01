package com.github.jon7even.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface AuthorizationService {
    boolean processAuthorization(Update update);
}
