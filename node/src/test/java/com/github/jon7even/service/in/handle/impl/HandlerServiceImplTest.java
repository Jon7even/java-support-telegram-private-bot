package com.github.jon7even.service.in.handle.impl;

import com.github.jon7even.service.in.handle.factory.UserHandlerFactory;
import com.github.jon7even.service.in.status.UserStatusService;
import com.github.jon7even.setup.TestDataFactory;
import com.github.jon7even.telegram.BotState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тестирование сервиса обработки данных от пользователей {@link HandlerServiceImpl}
 *
 * @author Jon7even
 * @version 2.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование методов сервиса HandlerServiceImpl")
public class HandlerServiceImplTest extends TestDataFactory {

    @InjectMocks
    private HandlerServiceImpl handlerService;

    @Mock
    private UserStatusService userStatusService;

    @Mock
    private UserHandlerFactory userHandlerFactory;

    @Mock
    private StandardTextHandlerImpl standardTextHandler;

    @Mock
    private StandardCallbackHandlerImpl standardCallbackHandler;

    @Mock
    private Update update;

    @BeforeEach
    public void setUpEntity() {
        initMessage();
    }

    @Test
    @DisplayName("Должен обработать текстовое сообщение, если базовая команда")
    public void processTextMessage_Success() {
        when(update.getMessage()).thenReturn(expectedMessage);
        when(userStatusService.getBotStateForUser(chatIdOne)).thenReturn(BotState.MAIN_ASK);
        when(userHandlerFactory.getHandlerForUser(BotState.MAIN_ASK)).thenReturn(standardTextHandler);
        doNothing().when(standardTextHandler).handle(update);

        handlerService.processTextMessage(update);

        verify(userStatusService, times(1)).getBotStateForUser(chatIdOne);
        verify(userHandlerFactory, times(1)).getHandlerForUser(BotState.MAIN_ASK);
        verify(standardTextHandler, times(1)).handle(update);
    }

    @Test
    @DisplayName("Должен обработать нажатие на клавиатуру, если базовая команда")
    public void handleQuery_Success() {
        when(update.getCallbackQuery()).thenReturn(callbackQueryMessage);
        when(userStatusService.getBotStateForUser(chatIdOne)).thenReturn(BotState.MAIN_CALLBACK);
        when(userHandlerFactory.getHandlerForUser(BotState.MAIN_CALLBACK)).thenReturn(standardCallbackHandler);
        doNothing().when(standardCallbackHandler).handle(update);

        handlerService.processCallbackQuery(update);

        verify(userStatusService, times(1)).getBotStateForUser(chatIdOne);
        verify(userHandlerFactory, times(1)).getHandlerForUser(BotState.MAIN_CALLBACK);
        verify(standardCallbackHandler, times(1)).handle(update);
    }
}