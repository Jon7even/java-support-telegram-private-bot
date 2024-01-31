package com.github.jon7even.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import static com.github.jon7even.RabbitQueue.ANSWER_MESSAGE;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void producerAnswerText(SendMessage sendMessage) {
        rabbitTemplate.convertAndSend(ANSWER_MESSAGE, sendMessage);
    }

    @Override
    public void producerAnswerEditText(EditMessageText editMessageText) {
        rabbitTemplate.convertAndSend(ANSWER_MESSAGE, editMessageText);
    }
}
