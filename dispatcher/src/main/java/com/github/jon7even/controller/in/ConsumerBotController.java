package com.github.jon7even.controller.in;

import com.github.jon7even.controller.out.SenderBotClient;
import com.github.jon7even.mapper.UserMapper;
import com.github.jon7even.service.in.AuthorizationService;
import com.github.jon7even.service.in.MainQuickService;
import com.github.jon7even.service.in.UpdateProducerService;
import com.github.jon7even.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import static com.github.jon7even.configuration.RabbitQueue.CALLBACK_QUERY_UPDATE;
import static com.github.jon7even.configuration.RabbitQueue.TEXT_MESSAGE_UPDATE;
import static com.github.jon7even.constants.DefaultMessagesLogs.WE_NOT_SUPPORT;

@Slf4j
@Component
public class ConsumerBotController {
    private final SenderBotClient senderBotClient;
    private final UpdateProducerService updateProducer;
    private final AuthorizationService authorizationService;
    private final MainQuickService mainQuickService;

    public ConsumerBotController(
            SenderBotClient senderBotClient, UpdateProducerService updateProducer,
            AuthorizationService authorizationService,
            MainQuickService mainQuickService) {
        this.senderBotClient = senderBotClient;
        this.updateProducer = updateProducer;
        this.authorizationService = authorizationService;
        this.mainQuickService = mainQuickService;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null!");
            return;
        }

        if (update.hasMessage()) {
            distributeMessagesByType(update);
        } else if (update.hasCallbackQuery()) {
            distributeCallbackByType(update);
        } else {
            log.error("Unsupported message type is received: update={}", update);
        }
    }

    private void distributeCallbackByType(Update update) {
        if (authorizationService.processAuthorizationForCallBack(update.getCallbackQuery().getMessage().getChatId())) {
            processCallbackQuery(update);
        } else {
            var sendMessage = MessageUtils.buildAnswerWithMessage(
                    update.getMessage(), String.format("Такую команду %s", WE_NOT_SUPPORT)
            );
            setView(sendMessage);
        }
    }

    private void distributeMessagesByType(Update update) {
        Message message = update.getMessage();

        if (authorizationService.processAuthorization(UserMapper.INSTANCE.toDtoFromMessage(message.getChat(),
                message.getText()))) {
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
            var sendMessage = MessageUtils.buildAnswerWithMessage(
                    message, String.format("Такую команду %s", WE_NOT_SUPPORT)
            );
            setView(sendMessage);
        }
    }

    private void processTextMessage(Update update) {
        if (mainQuickService.existBaseCommand(update.getMessage().getText())) {
            setView(mainQuickService.processQuickAnswer(update));
        } else {
            updateProducer.produceText(TEXT_MESSAGE_UPDATE, update);
        }
    }

    private void processCallbackQuery(Update update) {
        updateProducer.produceCallBackQuery(CALLBACK_QUERY_UPDATE, update);
    }

    private void processDocument(Update update) {
        var sendMessage = MessageUtils.buildAnswerWithMessage(
                update.getMessage(), String.format("Получение документов %s", WE_NOT_SUPPORT)
        );
        setView(sendMessage);
    }

    private void processPhotoMessage(Update update) {
        var sendMessage = MessageUtils.buildAnswerWithMessage(
                update.getMessage(), String.format("Получение фото %s", WE_NOT_SUPPORT)
        );
        setView(sendMessage);
    }

    private void processAudioMessage(Update update) {
        var sendMessage = MessageUtils.buildAnswerWithMessage(
                update.getMessage(), String.format("Получение аудио %s", WE_NOT_SUPPORT)
        );
        setView(sendMessage);
    }

    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = MessageUtils.buildAnswerWithMessage(
                update.getMessage(), String.format("Получение данного типа сообщений %s", WE_NOT_SUPPORT)
        );
        setView(sendMessage);
    }

    private void setView(SendMessage sendMessage) {
        senderBotClient.sendAnswerMessage(sendMessage);
    }

}
