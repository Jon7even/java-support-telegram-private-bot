package com.github.jon7even.service.in.impl;

import com.github.jon7even.telegram.constants.DefaultBaseMessagesToSend;
import com.github.jon7even.utils.MessageUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import static com.github.jon7even.telegram.constants.DefaultSystemMessagesToSend.ERROR_TO_EXECUTION_FOR_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Тестирование сервиса быстрых ответов {@link MainQuickServiceImpl}
 *
 * @author Jon7even
 * @version 2.0
 */
@DisplayName("Тестирование методов сервиса MainQuickServiceImpl")
@ExtendWith(MockitoExtension.class)
public class MainQuickServiceImplTest {

    @InjectMocks
    private MainQuickServiceImpl mainQuickService;

    @Mock
    private Update update;

    @Test
    @DisplayName("Должен правильно ответить на команду /start")
    public void processQuickAnswer_ReturnsStartCommand() {
        Message messageId = Message.builder()
                .chat(Chat.builder().id(1L).type("private").build())
                .text("/start")
                .build();
        SendMessage expectedMessageId = MessageUtils.buildAnswerWithMessage(
                messageId, DefaultBaseMessagesToSend.START_TEXT
        );

        when(update.getMessage()).thenReturn(messageId);

        SendMessage actualResponse = mainQuickService.processQuickAnswer(update);

        assertThat(actualResponse)
                .isNotNull()
                .isEqualTo(expectedMessageId);
    }

    @Test
    @DisplayName("Должен правильно ответить на команду /help")
    public void testProcessQuickAnswer_ReturnsHelpCommand() {
        Message messageId = Message.builder()
                .chat(Chat.builder().id(1L).type("private").build())
                .text("/help")
                .build();
        SendMessage expectedMessageId = MessageUtils.buildAnswerWithMessage(
                messageId, DefaultBaseMessagesToSend.HELP_TEXT
        );

        when(update.getMessage()).thenReturn(messageId);

        SendMessage actualResponse = mainQuickService.processQuickAnswer(update);

        assertThat(actualResponse)
                .isNotNull()
                .isEqualTo(expectedMessageId);
    }

    @Test
    @DisplayName("Должен распознать неправильную команду и выдать сообщение с ошибкой")
    public void testProcessQuickAnswer_ReturnsErrorMessage() {
        Message messageId = Message.builder()
                .chat(Chat.builder().id(1L).type("private").build())
                .text("/test")
                .build();
        SendMessage expectedMessageId = MessageUtils.buildAnswerWithMessage(
                messageId, ERROR_TO_EXECUTION_FOR_USER
        );

        when(update.getMessage()).thenReturn(messageId);

        SendMessage actualResponse = mainQuickService.processQuickAnswer(update);

        assertThat(actualResponse)
                .isNotNull()
                .isEqualTo(expectedMessageId);
    }

    @Test
    @DisplayName("Если введена базовая команда должен вернуть true")
    public void existsBaseCommand_ReturnsTrue() {
        String commandForChecking = "/start";
        boolean expectedResult = true;

        boolean actualResponse = mainQuickService.existsBaseCommand(commandForChecking);

        assertThat(actualResponse)
                .isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Если не введена базовая команда должен вернуть false")
    public void existsBaseCommand_ReturnsFalse() {
        String commandForChecking = "/test";
        boolean expectedResult = false;

        boolean actualResponse = mainQuickService.existsBaseCommand(commandForChecking);

        assertThat(actualResponse)
                .isEqualTo(expectedResult);
    }
}