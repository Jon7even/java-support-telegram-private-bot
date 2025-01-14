package com.github.jon7even.service.out.producer.impl;

import com.github.jon7even.setup.GenericTests;
import com.github.jon7even.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Collections;

import static com.github.jon7even.telegram.constants.DefaultSystemMessagesToSend.ERROR_TO_SEND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Тестирование сервиса формирования ответов для пользователей {@link SenderMessageServiceImpl}
 *
 * @author Jon7even
 * @version 2.0
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование методов сервиса SenderMessageServiceImpl")
public class SenderMessageServiceImplTest extends GenericTests {

    @InjectMocks
    private SenderMessageServiceImpl senderMessageService;

    @Mock
    private ProducerServiceImpl producerService;

    @BeforeEach
    public void setUpEntity() {
        initMessage();
    }

    @Test
    @DisplayName("Должен отправить простое сообщение с текстом")
    public void sendText_Success() {
        SendMessage expectedMessage = MessageUtils.buildAnswerWithText(chatIdOne, expectedSendMessage.getText());

        doNothing().when(producerService).producerAnswerText(expectedMessage);

        senderMessageService.sendText(chatIdOne, expectedSendMessage.getText());

        verify(producerService, times(1)).producerAnswerText(expectedMessage);
        verify(producerService, never()).producerAnswerEditText(any());
    }

    @Test
    @DisplayName("Должен отправить простое сообщение с текстом и новой клавиатурой")
    public void sendTextAndMarkup_Success() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(Collections.emptyList());
        SendMessage expectedMessage = MessageUtils.buildAnswerWithTextAndMarkup(
                chatIdOne, expectedSendMessage.getText(), inlineKeyboardMarkup
        );

        doNothing().when(producerService).producerAnswerText(expectedMessage);

        senderMessageService.sendTextAndMarkup(chatIdOne, expectedSendMessage.getText(), inlineKeyboardMarkup);

        verify(producerService, times(1)).producerAnswerText(expectedMessage);
        verify(producerService, never()).producerAnswerEditText(any());
    }

    @Test
    @DisplayName("Должен отправить простое сообщение с текстом ошибки")
    public void sendError_Success() {
        SendMessage expectedMessage = MessageUtils.buildAnswerWithText(
                chatIdOne, String.format("%s %s", ERROR_TO_SEND, expectedSendMessage.getText())
        );

        doNothing().when(producerService).producerAnswerText(expectedMessage);

        senderMessageService.sendError(chatIdOne, expectedSendMessage.getText());

        verify(producerService, times(1)).producerAnswerText(expectedMessage);
        verify(producerService, never()).producerAnswerEditText(any());
    }

    @Test
    @DisplayName("Должен отправить отредактированное сообщение")
    public void sendEditText_Success() {
        String expectedText = expectedEditMessageText.getText();
        int messageId = expectedEditMessageText.getMessageId();
        EditMessageText expectedMessage = MessageUtils.buildAnswerWithEditText(chatIdOne, expectedText, messageId);

        doNothing().when(producerService).producerAnswerEditText(expectedMessage);

        senderMessageService.sendEditText(chatIdOne, expectedText, messageId);

        verify(producerService, times(1)).producerAnswerEditText(expectedMessage);
        verify(producerService, never()).producerAnswerText(any());
    }
}