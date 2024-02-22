package com.github.jon7even.service.consumer;

import com.github.jon7even.service.MainService;
import com.github.jon7even.service.producer.SenderMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.jon7even.configuration.RabbitQueue.*;
import static com.github.jon7even.constants.DefaultMessageError.ERROR_RECEIVE;
import static com.github.jon7even.constants.DefaultMessagesLogs.WE_NOT_SUPPORT;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {
    private final MainService mainService;
    private final SenderMessageService senderMessageService;

    @Override
    @RabbitListener(queues = TEXT_MESSAGE_UPDATE)
    public void consumeTextMessageUpdates(Update update) {
        log.debug("Получена новая TEXT очередь update={}", update);
        log.info("Получена новая TEXT очередь text={}", update.getMessage().getText());
        try {
            mainService.processTextMessage(update);
        } catch (RuntimeException exception) {
            senderMessageService.sendError(update.getMessage().getChatId(), ERROR_RECEIVE);
            log.error("Произошла ошибка в процессе ответа пользователю Error: {}", exception.getMessage());
        }
    }

    @Override
    @RabbitListener(queues = CALLBACK_QUERY_UPDATE)
    public void consumeCallbackQueryUpdates(Update update) {
        log.debug("Получена новая CALLBACK очередь update={}", update);
        log.info("Получена новая CALLBACK очередь data={}", update.getCallbackQuery().getData());
        try {
            mainService.processCallbackQuery(update);
        } catch (RuntimeException exception) {
            senderMessageService.sendError(update.getMessage().getChatId(), ERROR_RECEIVE);
            log.error("Произошла ошибка в процессе ответа пользователю Error: {}", exception.getMessage());
        }
    }

    @Override
    @RabbitListener(queues = DOC_MESSAGE_UPDATE)
    public void consumeDocMessageUpdates(Update update) {
        log.info("Получена новая DOC очередь doc={}", update);
        senderMessageService.sendError(update.getMessage().getChatId(), ERROR_RECEIVE);
        log.error("Произошла ошибка в процессе ответа пользователю Error: {}", WE_NOT_SUPPORT);
    }

    @Override
    @RabbitListener(queues = PHOTO_MESSAGE_UPDATE)
    public void consumePhotoMessageUpdates(Update update) {
        log.info("Получена новая PHOTO очередь photo={}", update);
        senderMessageService.sendError(update.getMessage().getChatId(), ERROR_RECEIVE);
        log.error("Произошла ошибка в процессе ответа пользователю Error: {}", WE_NOT_SUPPORT);
    }

    @Override
    @RabbitListener(queues = AUDIO_MESSAGE_UPDATE)
    public void consumeAudioMessageUpdates(Update update) {
        log.info("Получена новая AUDIO очередь audio={}", update);
        senderMessageService.sendError(update.getMessage().getChatId(), ERROR_RECEIVE);
        log.error("Произошла ошибка в процессе ответа пользователю Error: {}", WE_NOT_SUPPORT);
    }
}
