package com.github.jon7even.service.out;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AnswerConsumerService {
    void consume(SendMessage sendMessage);
}
