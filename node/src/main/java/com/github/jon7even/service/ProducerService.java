package com.github.jon7even.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public interface ProducerService {
    void producerAnswerText(SendMessage sendMessage);
    void producerAnswerEditText(EditMessageText editMessageText);
}
