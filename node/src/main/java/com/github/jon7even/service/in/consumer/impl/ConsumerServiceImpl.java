package com.github.jon7even.service.in.consumer.impl;

import com.github.jon7even.service.in.consumer.ConsumerService;
import com.github.jon7even.service.in.handle.HandlerService;
import com.github.jon7even.service.out.producer.SenderMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.jon7even.configuration.RabbitQueue.AUDIO_MESSAGE_UPDATE;
import static com.github.jon7even.configuration.RabbitQueue.CALLBACK_QUERY_UPDATE;
import static com.github.jon7even.configuration.RabbitQueue.DOC_MESSAGE_UPDATE;
import static com.github.jon7even.configuration.RabbitQueue.PHOTO_MESSAGE_UPDATE;
import static com.github.jon7even.configuration.RabbitQueue.TEXT_MESSAGE_UPDATE;
import static com.github.jon7even.telegram.constants.DefaultMessageLogError.ERROR_TO_EXECUTION_FOR_USER;
import static com.github.jon7even.telegram.constants.DefaultSystemMessagesToSend.ERROR_RECEIVE;
import static com.github.jon7even.telegram.constants.DefaultSystemMessagesToSend.WE_NOT_SUPPORT;

/**
 * Реализация сервиса слушателя входящих сообщений от API Telegram {@link ConsumerService}
 *
 * @author Jon7even
 * @version 2.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final HandlerService handlerService;

    private final SenderMessageService senderMessageService;

    @Override
    @RabbitListener(queues = TEXT_MESSAGE_UPDATE)
    public void consumeTextMessageUpdates(Update update) {
        log.debug("Получена новая TEXT очередь update={}", update);
        log.info("Получена новая TEXT очередь text={}", update.getMessage().getText());
        try {
            handlerService.processTextMessage(update);
        } catch (RuntimeException exception) {
            senderMessageService.sendError(update.getMessage().getChatId(), ERROR_RECEIVE);
            log.error("{} {}", ERROR_TO_EXECUTION_FOR_USER, exception.getMessage());
        }
    }

    @Override
    @RabbitListener(queues = CALLBACK_QUERY_UPDATE)
    public void consumeCallbackQueryUpdates(Update update) {
        log.debug("Получена новая CALLBACK очередь update={}", update);
        log.info("Получена новая CALLBACK очередь data={}", update.getCallbackQuery().getData());
        try {
            handlerService.processCallbackQuery(update);
        } catch (RuntimeException exception) {
            senderMessageService.sendError(update.getMessage().getChatId(), ERROR_RECEIVE);
            log.error("{} {}", ERROR_TO_EXECUTION_FOR_USER, exception.getMessage());
        }
    }

    @Override
    @RabbitListener(queues = DOC_MESSAGE_UPDATE)
    public void consumeDocMessageUpdates(Update update) {
        log.info("Получена новая DOC очередь doc={}", update);
        senderMessageService.sendError(update.getMessage().getChatId(), ERROR_RECEIVE);
        log.error("{} {}", ERROR_TO_EXECUTION_FOR_USER, WE_NOT_SUPPORT);
    }

    @Override
    @RabbitListener(queues = PHOTO_MESSAGE_UPDATE)
    public void consumePhotoMessageUpdates(Update update) {
        log.info("Получена новая PHOTO очередь photo={}", update);
        senderMessageService.sendError(update.getMessage().getChatId(), ERROR_RECEIVE);
        log.error("{} {}", ERROR_TO_EXECUTION_FOR_USER, WE_NOT_SUPPORT);
    }

    @Override
    @RabbitListener(queues = AUDIO_MESSAGE_UPDATE)
    public void consumeAudioMessageUpdates(Update update) {
        log.info("Получена новая AUDIO очередь audio={}", update);
        senderMessageService.sendError(update.getMessage().getChatId(), ERROR_RECEIVE);
        log.error("{} {}", ERROR_TO_EXECUTION_FOR_USER, WE_NOT_SUPPORT);
    }
}