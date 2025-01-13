package com.github.jon7even.service.in.impl;

import com.github.jon7even.controller.out.SenderBotClient;
import com.github.jon7even.service.in.AnswerConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.github.jon7even.configuration.RabbitQueue.ANSWER_MESSAGE;

/**
 * Реализация сервиса {@link AnswerConsumerService} слушателя RabbitMq для отправки сообщений в Telegram bot.
 *
 * @author Jon7even
 * @version 2.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerConsumerServiceImpl implements AnswerConsumerService {

    private final SenderBotClient senderBotClient;

    @Override
    @RabbitListener(queues = ANSWER_MESSAGE)
    public void consume(SendMessage sendMessage) {
        log.debug("Начинаем отправлять ответ [text={}]", sendMessage.getText());
        senderBotClient.sendAnswerMessage(sendMessage);
    }
}