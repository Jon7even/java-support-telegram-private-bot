package com.github.jon7even.service;

import com.github.jon7even.controller.UpdateController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.github.jon7even.configuration.RabbitQueue.ANSWER_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerConsumerServiceImpl implements AnswerConsumerService {
    private final UpdateController updateController;

    @Override
    @RabbitListener(queues = ANSWER_MESSAGE)
    public void consume(SendMessage sendMessage) {
        log.debug("Начинаем отправлять ответ text={}", sendMessage.getText());
        updateController.setView(sendMessage);
    }
}
