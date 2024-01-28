package com.github.jon7even.controller;

import com.github.jon7even.service.UpdateProducerService;
import com.github.jon7even.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.jon7even.RabbitQueue.TEXT_MESSAGE_UPDATE;
import static com.github.jon7even.constants.DefaultMessagesLogs.WE_NOT_SUPPORT;

@Slf4j
@Component
public class UpdateController {
    private final TelegramBot telegramBot;
    private final UpdateProducerService updateProducer;

    public UpdateController(@Lazy TelegramBot telegramBot, UpdateProducerService updateProducer) {
        this.telegramBot = telegramBot;
        this.updateProducer = updateProducer;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null!");
            return;
        }

        if (update.hasMessage()) {
            distributeMessagesByType(update);
        } else {
            log.error("Unsupported message type is received: update={}", update);
        }
    }

    private void distributeMessagesByType(Update update) {
        Message message = update.getMessage();
        if (message.hasText()) {
            processTextMessage(update);
        } else if (message.hasAudio()) {
            processDocument(update);
        } else if (message.hasDocument()) {
            processPhotoMessage(update);
        } else if (message.hasPhoto()) {
            processAudioMessage(update);
        } else {
            setUnsupportedMessageTypeView(update);
        }
    }

    private void processTextMessage(Update update) {
        updateProducer.produce(TEXT_MESSAGE_UPDATE, update);

        var sendMessage = MessageUtils.buildAnswerWithText(
                update.getMessage(), "Ваше сообщение получено!"
        );

        setView(sendMessage);
    }

    public void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

    private void processDocument(Update update) {
        var sendMessage = MessageUtils.buildAnswerWithText(
                update.getMessage(), String.format("Получение документов %s", WE_NOT_SUPPORT)
        );

        setView(sendMessage);
    }

    private void processPhotoMessage(Update update) {
        var sendMessage = MessageUtils.buildAnswerWithText(
                update.getMessage(), String.format("Получение фото %s", WE_NOT_SUPPORT)
        );

        setView(sendMessage);
    }

    private void processAudioMessage(Update update) {
        var sendMessage = MessageUtils.buildAnswerWithText(
                update.getMessage(), String.format("Получение аудио %s", WE_NOT_SUPPORT)
        );

        setView(sendMessage);
    }

    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = MessageUtils.buildAnswerWithText(
                update.getMessage(), String.format("Получение данного типа сообщений %s", WE_NOT_SUPPORT)
        );

        setView(sendMessage);
    }

}
