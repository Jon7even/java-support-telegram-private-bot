package com.github.jon7even.service.in;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MainQuickService {
    boolean existBaseCommand(String command);
    SendMessage processQuickAnswer(Update update);
}
