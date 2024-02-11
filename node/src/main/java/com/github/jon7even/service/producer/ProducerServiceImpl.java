package com.github.jon7even.service.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import static com.github.jon7even.configuration.RabbitQueue.ANSWER_MESSAGE;
import static com.github.jon7even.constants.DefaultMessageError.ERROR_SEND_TEXT;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {
    private final RabbitTemplate rabbitTemplate;
    private final SenderMessageService senderMessageService;

    @Override
    public void producerAnswerText(SendMessage sendMessage) {
        try {
            rabbitTemplate.convertAndSend(ANSWER_MESSAGE, sendMessage);
        } catch (RuntimeException exception) {
            senderMessageService.sendError(Long.valueOf(sendMessage.getChatId()), ERROR_SEND_TEXT);
            log.error("Произошла ошибка в процессе ответа пользователю Text: {} Error: {}",
                    sendMessage, exception.getMessage());
        }
    }

    @Override
    public void producerAnswerEditText(EditMessageText editMessageText) {
        try {
            rabbitTemplate.convertAndSend(ANSWER_MESSAGE, editMessageText);
        } catch (RuntimeException exception) {
            senderMessageService.sendError(Long.valueOf(editMessageText.getChatId()), ERROR_SEND_TEXT);
            log.error("Произошла ошибка в процессе ответа пользователю EditText: {} Error: {}",
                    editMessageText, exception.getMessage());
        }
    }
}
