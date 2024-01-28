package com.github.jon7even.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AnswerConsumerService {
    void consume(SendMessage sendMessage);
}
