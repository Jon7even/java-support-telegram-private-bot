package com.github.jon7even.service.out.impl;

import com.github.jon7even.service.out.UpdateProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Реализация сервиса {@link UpdateProducerService} для постановки запросов от пользователей в очередь для дальнейшей
 * обработки другими сервисами.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Использует {@link RabbitTemplate}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateProducerServiceImpl implements UpdateProducerService {

    public final RabbitTemplate rabbitTemplate;

    @Override
    public void produceText(String rabbitQueue, Update update) {
        log.debug("Конвертирую полученное сообщение с чата [text={}]", update.getMessage().getText());
        rabbitTemplate.convertAndSend(rabbitQueue, update);
    }

    @Override
    public void produceCallBackQuery(String rabbitQueue, Update update) {
        log.debug("Конвертирую полученное сообщение c клавиатуры [data={}]", update.getCallbackQuery().getData());
        rabbitTemplate.convertAndSend(rabbitQueue, update);
    }
}