package com.github.jon7even.service.out.producer.impl;

import com.github.jon7even.exception.ApplicationException;
import com.github.jon7even.service.out.producer.SenderMessageService;
import com.github.jon7even.setup.GenericTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static com.github.jon7even.configuration.RabbitQueue.ANSWER_MESSAGE;
import static com.github.jon7even.telegram.constants.DefaultSystemMessagesToSend.ERROR_SEND_TEXT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

/**
 * Тестирование сервиса для отправки ответов в RabbitMq {@link ProducerServiceImpl}
 *
 * @author Jon7even
 * @version 2.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование методов сервиса ProducerServiceImpl")
public class ProducerServiceImplTest extends GenericTests {

    @InjectMocks
    private ProducerServiceImpl producerService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private SenderMessageService senderMessageService;

    @BeforeEach
    public void setUpEntity() {
        initMessage();
    }

    @Test
    @DisplayName("Должен отправить простое сообщение")
    public void producerAnswerText_Success() {
        doNothing().when(rabbitTemplate).convertAndSend(ANSWER_MESSAGE, expectedSendMessage);

        producerService.producerAnswerText(expectedSendMessage);

        verify(rabbitTemplate, times(1)).convertAndSend(ANSWER_MESSAGE, expectedSendMessage);
        verifyNoInteractions(senderMessageService);
    }

    @Test
    @DisplayName("Должен отправить отредактированное сообщение")
    public void producerAnswerEditText_Success() {
        doNothing().when(rabbitTemplate).convertAndSend(ANSWER_MESSAGE, expectedEditMessageText);

        producerService.producerAnswerEditText(expectedEditMessageText);

        verify(rabbitTemplate, times(1)).convertAndSend(ANSWER_MESSAGE, expectedEditMessageText);
        verifyNoInteractions(senderMessageService);
    }

    @Test
    @DisplayName("Должен выбросить исключение при отправке текстового сообщения")
    public void producerAnswerText_throwsApplicationException() {
        verifyNoInteractions(senderMessageService);

        String expectedErrorMessage = "any Error";

        doThrow(new ApplicationException(expectedErrorMessage))
                .when(rabbitTemplate).convertAndSend(ANSWER_MESSAGE, expectedSendMessage);

        producerService.producerAnswerText(expectedSendMessage);

        assertThatThrownBy(() -> rabbitTemplate.convertAndSend(ANSWER_MESSAGE, expectedSendMessage))
                .isInstanceOf(ApplicationException.class)
                .hasMessage(expectedErrorMessage);
    }

    @Test
    @DisplayName("Должна сработать правильная логика обработки исключения при отправке текстового сообщения")
    public void producerAnswerText_throwsApplicationExceptionAndSendMessageBySender() {
        verifyNoInteractions(senderMessageService);

        String expectedErrorMessage = "any Error";

        doThrow(new ApplicationException(expectedErrorMessage))
                .when(rabbitTemplate).convertAndSend(ANSWER_MESSAGE, expectedSendMessage);
        doNothing().when(senderMessageService).sendError(chatIdOne, ERROR_SEND_TEXT);

        producerService.producerAnswerText(expectedSendMessage);

        verify(rabbitTemplate, times(1)).convertAndSend(ANSWER_MESSAGE, expectedSendMessage);
        verify(senderMessageService, times(1)).sendError(chatIdOne, ERROR_SEND_TEXT);
    }

    @Test
    @DisplayName("Должен выбросить исключение при отправке отредактированного сообщения")
    public void producerAnswerEditText_throwsApplicationException() {
        verifyNoInteractions(senderMessageService);

        String expectedErrorMessage = "any Error";

        doThrow(new ApplicationException(expectedErrorMessage))
                .when(rabbitTemplate).convertAndSend(ANSWER_MESSAGE, expectedEditMessageText);

        producerService.producerAnswerEditText(expectedEditMessageText);

        assertThatThrownBy(() -> rabbitTemplate.convertAndSend(ANSWER_MESSAGE, expectedEditMessageText))
                .isInstanceOf(ApplicationException.class)
                .hasMessage(expectedErrorMessage);
    }

    @Test
    @DisplayName("Должна сработать правильная логика обработки исключения при отправке отредактированного сообщения")
    public void producerAnswerEditText_throwsApplicationExceptionAndSendMessageBySender() {
        verifyNoInteractions(senderMessageService);

        String expectedErrorMessage = "any Error";

        doThrow(new ApplicationException(expectedErrorMessage))
                .when(rabbitTemplate).convertAndSend(ANSWER_MESSAGE, expectedEditMessageText);
        doNothing().when(senderMessageService).sendError(chatIdOne, ERROR_SEND_TEXT);

        producerService.producerAnswerEditText(expectedEditMessageText);

        verify(rabbitTemplate, times(1)).convertAndSend(ANSWER_MESSAGE, expectedEditMessageText);
        verify(senderMessageService, times(1)).sendError(chatIdOne, ERROR_SEND_TEXT);
    }
}