package com.github.jon7even.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateProducerServiceImpl implements UpdateProducerService {
    public final RabbitTemplate rabbitTemplate;

    @Override
    public void produce(String rabbitQueue, Update update) {
        log.debug("Начинаем конвертировать полученное сообщение text={}", update.getMessage().getText());
        rabbitTemplate.convertAndSend(rabbitQueue, update);
    }
}
