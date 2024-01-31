package com.github.jon7even.controller;

import com.github.jon7even.configuration.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final UpdateController updateController;

    public TelegramBot(BotConfig botConfig, UpdateController updateController) {
        super(botConfig.getToken());
        this.botConfig = botConfig;
        this.updateController = updateController;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateController.processUpdate(update);
    }

    public void sendAnswerMessage(SendMessage message) {
        if (message != null) {
            try {
                execute(message);
            } catch (TelegramApiException exception) {
                log.error("Произошла ошибка при отправке ответного сообщения: {}", exception.getMessage());
            }
        }
    }
}
