package com.github.jon7even.service.in.consumer.impl;

import com.github.jon7even.exception.ApplicationException;
import com.github.jon7even.service.in.handle.HandlerService;
import com.github.jon7even.service.out.producer.SenderMessageService;
import com.github.jon7even.setup.GenericTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.jon7even.telegram.constants.DefaultSystemMessagesToSend.ERROR_RECEIVE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Тестирование сервиса получения сообщений из RabbitMq {@link ConsumerServiceImpl}
 *
 * @author Jon7even
 * @version 2.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование методов сервиса ConsumerServiceImpl")
public class ConsumerServiceImplTest extends GenericTests {

    @InjectMocks
    private ConsumerServiceImpl consumerService;

    @Mock
    private HandlerService handlerService;

    @Mock
    private SenderMessageService senderMessageService;

    @Mock
    private Update update;

    @BeforeEach
    public void setUpEntity() {
        initMessage();
    }

    @Test
    @DisplayName("Должен получить сообщение с простым текстом")
    public void consumeTextMessageUpdates_Success() {
        doNothing().when(handlerService).processTextMessage(update);
        when(update.getMessage()).thenReturn(expectedMessage);

        consumerService.consumeTextMessageUpdates(update);

        verify(handlerService, times(1)).processTextMessage(update);
        verifyNoInteractions(senderMessageService);
    }

    @Test
    @DisplayName("Должен выбросить исключение при обработке полученного текстового сообщения")
    public void consumeTextMessageUpdates_throwsApplicationException() {
        verifyNoInteractions(senderMessageService);

        String expectedErrorMessage = "any Error";

        when(update.getMessage()).thenReturn(expectedMessage);
        doThrow(new ApplicationException(expectedErrorMessage))
                .when(handlerService).processTextMessage(update);

        consumerService.consumeTextMessageUpdates(update);

        assertThatThrownBy(() -> handlerService.processTextMessage(update))
                .isInstanceOf(ApplicationException.class)
                .hasMessage(expectedErrorMessage);
    }

    @Test
    @DisplayName("Должна сработать правильная логика обработки исключения при обработке полученного сообщения")
    public void consumeTextMessageUpdates_throwsApplicationExceptionAndSendMessageBySender() {
        verifyNoInteractions(senderMessageService);

        String expectedErrorMessage = "any Error";

        when(update.getMessage()).thenReturn(expectedMessage);
        doThrow(new ApplicationException(expectedErrorMessage))
                .when(handlerService).processTextMessage(update);

        consumerService.consumeTextMessageUpdates(update);

        verify(handlerService, times(1)).processTextMessage(update);
        verify(senderMessageService, times(1)).sendError(expectedMessage.getChatId(), ERROR_RECEIVE);
    }

    @Test
    @DisplayName("Должен получить сообщение с нажатой клавиатуры")
    public void consumeCallbackQueryUpdates_Success() {
        doNothing().when(handlerService).processCallbackQuery(update);
        when(update.getCallbackQuery()).thenReturn(new CallbackQuery());

        consumerService.consumeCallbackQueryUpdates(update);

        verify(handlerService, times(1)).processCallbackQuery(update);
        verifyNoInteractions(senderMessageService);
    }

    @Test
    @DisplayName("Должен выбросить исключение при обработке полученной нажатой клавиатуры")
    public void consumeCallbackQueryUpdates_throwsApplicationException() {
        verifyNoInteractions(senderMessageService);

        String expectedErrorMessage = "any Error";

        when(update.getCallbackQuery()).thenReturn(new CallbackQuery());
        when(update.getMessage()).thenReturn(expectedMessage);
        doThrow(new ApplicationException(expectedErrorMessage))
                .when(handlerService).processCallbackQuery(update);

        consumerService.consumeCallbackQueryUpdates(update);

        assertThatThrownBy(() -> handlerService.processCallbackQuery(update))
                .isInstanceOf(ApplicationException.class)
                .hasMessage(expectedErrorMessage);
    }

    @Test
    @DisplayName("Должна сработать правильная логика обработки исключения при обработке полученной нажатой клавиатуры")
    public void consumeCallbackQueryUpdates_throwsApplicationExceptionAndSendMessageBySender() {
        verifyNoInteractions(senderMessageService);

        String expectedErrorMessage = "any Error";

        when(update.getCallbackQuery()).thenReturn(new CallbackQuery());
        when(update.getMessage()).thenReturn(expectedMessage);
        doThrow(new ApplicationException(expectedErrorMessage))
                .when(handlerService).processCallbackQuery(update);

        consumerService.consumeCallbackQueryUpdates(update);

        verify(handlerService, times(1)).processCallbackQuery(update);
        verify(senderMessageService, times(1)).sendError(expectedMessage.getChatId(), ERROR_RECEIVE);
    }

    @Test
    @DisplayName("Должен получить сообщение с документом")
    public void consumeDocMessageUpdates_Success() {
        doNothing().when(senderMessageService).sendError(expectedMessage.getChatId(), ERROR_RECEIVE);
        when(update.getMessage()).thenReturn(expectedMessage);

        consumerService.consumeDocMessageUpdates(update);

        verify(senderMessageService, times(1)).sendError(expectedMessage.getChatId(), ERROR_RECEIVE);
    }

    @Test
    @DisplayName("Должен получить сообщение с фото")
    public void consumePhotoMessageUpdates_Success() {
        doNothing().when(senderMessageService).sendError(expectedMessage.getChatId(), ERROR_RECEIVE);
        when(update.getMessage()).thenReturn(expectedMessage);

        consumerService.consumePhotoMessageUpdates(update);

        verify(senderMessageService, times(1)).sendError(expectedMessage.getChatId(), ERROR_RECEIVE);
    }

    @Test
    @DisplayName("Должен получить сообщение с аудио")
    public void consumeAudioMessageUpdates_Success() {
        doNothing().when(senderMessageService).sendError(expectedMessage.getChatId(), ERROR_RECEIVE);
        when(update.getMessage()).thenReturn(expectedMessage);

        consumerService.consumeAudioMessageUpdates(update);

        verify(senderMessageService, times(1)).sendError(expectedMessage.getChatId(), ERROR_RECEIVE);
    }
}