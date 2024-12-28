package com.github.jon7even.controller.in.impl;

import com.github.jon7even.controller.in.HandleUpdateBot;
import com.github.jon7even.controller.out.SenderBotClient;
import com.github.jon7even.service.in.MainQuickService;
import com.github.jon7even.service.in.auth.AuthorizationService;
import com.github.jon7even.service.out.UpdateProducerService;
import com.github.jon7even.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import static com.github.jon7even.configuration.RabbitQueue.CALLBACK_QUERY_UPDATE;
import static com.github.jon7even.configuration.RabbitQueue.TEXT_MESSAGE_UPDATE;
import static com.github.jon7even.telegram.constants.DefaultSystemMessagesToSend.WE_NOT_SUPPORT;

/**
 * Реализация обработчика входящих сообщений от зарегистрированного Telegram бота
 *
 * @author Jon7even
 * @version 2.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HandleUpdateBotImpl implements HandleUpdateBot {

    private final SenderBotClient senderBotClient;

    private final UpdateProducerService updateProducer;

    private final AuthorizationService authorizationService;

    private final MainQuickService mainQuickService;

    /**
     * Реализация метода обработки входящих сообщений
     */
    @Override
    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Update является [null], срочно требуется проверить почему так случилось");
            return;
        }
        boolean isUserAuthorized = authorizationService.processAuthorization(update);

        if (isUserAuthorized) {
            if (update.hasMessage()) {
                distributeMessagesByType(update);
            } else if (update.hasCallbackQuery()) {
                distributeCallbackByType(update);
            } else {
                setUnsupportedMessageTypeView(update);
            }
        } else {
            sendMessageToChatAccessDenied(update);
        }
    }

    private void distributeMessagesByType(Update update) {
        var message = update.getMessage();
        log.debug("Начинаем обрабатывать обновление [пользователь отправил сообщение], его [message={}]", message);

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

    private void distributeCallbackByType(Update update) {
        var callbackQuery = update.getCallbackQuery();
        log.debug("Начинаем обрабатывать обновление [пользователь нажал на кнопку], его [callback={}]", callbackQuery);
        processCallbackQuery(update);
    }

    private void processTextMessage(Update update) {
        var textMessageByUser = update.getMessage().getText();
        boolean isBaseCommand = mainQuickService.existBaseCommand(textMessageByUser);

        if (isBaseCommand) {
            var messageForBaseCommand = mainQuickService.processQuickAnswer(update);
            setView(messageForBaseCommand);
        } else {
            updateProducer.produceText(TEXT_MESSAGE_UPDATE, update);
        }
    }

    private void processCallbackQuery(Update update) {
        updateProducer.produceCallBackQuery(CALLBACK_QUERY_UPDATE, update);
    }

    private void processDocument(Update update) {
        sendMessageToChatErrorNotSupportType("Получение документов", update.getMessage());
    }

    private void processPhotoMessage(Update update) {
        sendMessageToChatErrorNotSupportType("Получение фото ", update.getMessage());
    }

    private void processAudioMessage(Update update) {
        sendMessageToChatErrorNotSupportType("Получение аудио", update.getMessage());
    }

    private void setUnsupportedMessageTypeView(Update update) {
        sendMessageToChatErrorNotSupportType("Получение данного типа сообщений", update.getMessage());
    }

    private void sendMessageToChatErrorNotSupportType(String action, Message message) {
        log.error("Пользователь c [chatId={}] пытается сделать запрос на то что мы не умеем его message={}",
                message.getChatId(), message);
        var sendMessage = MessageUtils.buildAnswerWithText(
                message.getChatId(), action + WE_NOT_SUPPORT
        );
        setView(sendMessage);
    }

    private void sendMessageToChatAccessDenied(Update update) {
        Long chatId = -1L;

        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }

        log.warn("Пользователь c [chatId={}] не авторизовался, его [update={}]", chatId, update);
        var sendMessage = MessageUtils.buildAnswerWithText(
                chatId, String.format("Такую команду %s", WE_NOT_SUPPORT)
        );
        setView(sendMessage);
    }

    private void setView(SendMessage sendMessage) {
        senderBotClient.sendAnswerMessage(sendMessage);
    }
}