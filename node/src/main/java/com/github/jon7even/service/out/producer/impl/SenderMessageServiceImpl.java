package com.github.jon7even.service.out.producer.impl;

import com.github.jon7even.service.out.producer.ProducerService;
import com.github.jon7even.service.out.producer.SenderMessageService;
import com.github.jon7even.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static com.github.jon7even.telegram.constants.DefaultSystemMessagesToSend.ERROR_TO_SEND;

/**
 * Реализация сервиса {@link SenderMessageService} формирования и отправления ответов в RabbitMq.
 *
 * @author Jon7even
 * @version 2.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SenderMessageServiceImpl implements SenderMessageService {

    private final ProducerService producerService;

    @Override
    public void sendText(Long chatId, String textToSend) {
        sendMessageText(MessageUtils.buildAnswerWithText(chatId, textToSend));
    }

    @Override
    public void sendTextAndMarkup(Long chatId, String textToSend, InlineKeyboardMarkup inlineKeyboardMarkup) {
        sendMessageText(MessageUtils.buildAnswerWithTextAndMarkup(chatId, textToSend, inlineKeyboardMarkup));
    }

    @Override
    public void sendEditText(Long chatId, String textToEdit, Integer messageId) {
        sendEditMessageText(MessageUtils.buildAnswerWithEditText(chatId, textToEdit, messageId));
    }

    @Override
    public void sendError(Long chatId, String textToError) {
        sendMessageText(MessageUtils.buildAnswerWithText(chatId, String.format("%s %s", ERROR_TO_SEND, textToError)));
    }

    private void sendMessageText(SendMessage message) {
        log.debug("Подготовил сообщение для отправки: {}, начинаю отправлять...", message.getText());
        producerService.producerAnswerText(message);
    }

    private void sendEditMessageText(EditMessageText message) {
        log.debug("Подготовил сообщение для редактирования сообщения с id={} и текстом text={}, начинаю отправлять...",
                message.getMessageId(), message.getText());
        producerService.producerAnswerEditText(message);
    }
}