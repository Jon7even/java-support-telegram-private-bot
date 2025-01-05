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
    public void processQuickAnswer_StartCommand() {
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
    public void testProcessQuickAnswer_HelpCommand() {
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
}