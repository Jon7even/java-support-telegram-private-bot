package com.github.jon7even.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.jon7even.configuration.RabbitQueue.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {
    private final MainService mainService;

    @Override
    @RabbitListener(queues = TEXT_MESSAGE_UPDATE)
    public void consumeTextMessageUpdates(Update update) {
        log.debug("Получена новая TEXT очередь update={}", update);
        log.info("Получена новая TEXT очередь text={}", update.getMessage().getText());
        mainService.processTextMessage(update);
    }

    @Override
    @RabbitListener(queues = CALLBACK_QUERY_UPDATE)
    public void consumeCallbackQueryUpdates(Update update) {
        log.debug("Получена новая CALLBACK очередь update={}", update);
        log.info("Получена новая CALLBACK очередь data={}", update.getCallbackQuery().getData());
        mainService.processCallbackQuery(update);
    }

    @Override
    @RabbitListener(queues = DOC_MESSAGE_UPDATE)
    public void consumeDocMessageUpdates(Update update) {
        log.debug("Получена новая DOC очередь update={}", update);
        log.info("Получена новая DOC очередь doc={}", update);
    }

    @Override
    @RabbitListener(queues = PHOTO_MESSAGE_UPDATE)
    public void consumePhotoMessageUpdates(Update update) {
        log.debug("Получена новая PHOTO очередь update={}", update);
        log.info("Получена новая PHOTO очередь photo={}", update);
    }

    @Override
    @RabbitListener(queues = AUDIO_MESSAGE_UPDATE)
    public void consumeAudioMessageUpdates(Update update) {
        log.debug("Получена новая AUDIO очередь update={}", update);
        log.info("Получена новая AUDIO очередь audio={}", update);
    }
}
