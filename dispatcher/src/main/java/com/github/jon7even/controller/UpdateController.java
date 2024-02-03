package com.github.jon7even.controller;

import com.github.jon7even.service.AuthorizationService;
import com.github.jon7even.service.UpdateProducerService;
import com.github.jon7even.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.jon7even.configuration.RabbitQueue.CALLBACK_QUERY_UPDATE;
import static com.github.jon7even.configuration.RabbitQueue.TEXT_MESSAGE_UPDATE;
import static com.github.jon7even.constants.DefaultMessagesLogs.WE_NOT_SUPPORT;

@Slf4j
@Component
public class UpdateController {
    private final TelegramBot telegramBot;
    private final UpdateProducerService updateProducer;
    private final AuthorizationService authorizationService;

    public UpdateController(@Lazy TelegramBot telegramBot,
                            UpdateProducerService updateProducer,
                            AuthorizationService authorizationService) {
        this.telegramBot = telegramBot;
        this.updateProducer = updateProducer;
        this.authorizationService = authorizationService;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null!");
            return;
        }

        if (update.hasCallbackQuery()) {
            distributeCallbackByType(update);
        } else if (update.hasMessage()) {
            distributeMessagesByType(update);
        } else {
            log.error("Unsupported message type is received: update={}", update);
        }
    }

    private void distributeCallbackByType(Update update) {
            processCallbackQuery(update);
    }

    private void distributeMessagesByType(Update update) {
        if (authorizationService.processAuthorization(update)) {
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
        } else {
            var sendMessage = MessageUtils.buildAnswerWithText(
                    update.getMessage(), String.format("Такую команду %s", WE_NOT_SUPPORT)
            );
            setView(sendMessage);
        }
    }

    private void processTextMessage(Update update) {
        updateProducer.produceText(TEXT_MESSAGE_UPDATE, update);
    }

    private void processCallbackQuery(Update update) {
        updateProducer.produceCallBackQuery(CALLBACK_QUERY_UPDATE, update);
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
