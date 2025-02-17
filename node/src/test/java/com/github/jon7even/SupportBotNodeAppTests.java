package com.github.jon7even;

import com.github.jon7even.setup.GenericMainAppTests;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static com.github.jon7even.configuration.RabbitQueue.ANSWER_MESSAGE;
import static com.github.jon7even.configuration.RabbitQueue.AUDIO_MESSAGE_UPDATE;
import static com.github.jon7even.configuration.RabbitQueue.CALLBACK_QUERY_UPDATE;
import static com.github.jon7even.configuration.RabbitQueue.DOC_MESSAGE_UPDATE;
import static com.github.jon7even.configuration.RabbitQueue.PHOTO_MESSAGE_UPDATE;
import static com.github.jon7even.configuration.RabbitQueue.TEXT_MESSAGE_UPDATE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

/**
 * Проверка запуска приложения {@link NodeApp}
 *
 * @author Jon7even
 * @version 2.0
 */
@DisplayName("Тестирование запуска сервиса NodeApp")
class SupportBotNodeAppTests extends GenericMainAppTests {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private MessageSource messageSource;

    @Value("${localeTag}")
    @Autowired
    private Locale locale;

    @Test
    @DisplayName("Проверка загрузки контекста приложения")
    public void contextLoads() {
    }

    @Test
    @DisplayName("Проверка загрузки приложения без исключений")
    public void testMain() {
        assertThatCode(NodeApp::new).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Проверка создания очередей RabbitMq")
    public void testQueuesExist() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(rabbitAdmin.getQueueInfo(TEXT_MESSAGE_UPDATE)).isNotNull();
            softly.assertThat(rabbitAdmin.getQueueInfo(TEXT_MESSAGE_UPDATE).getName())
                    .isEqualTo(TEXT_MESSAGE_UPDATE);
            softly.assertThat(rabbitAdmin.getQueueInfo(CALLBACK_QUERY_UPDATE)).isNotNull();
            softly.assertThat(rabbitAdmin.getQueueInfo(CALLBACK_QUERY_UPDATE).getName())
                    .isEqualTo(CALLBACK_QUERY_UPDATE);
            softly.assertThat(rabbitAdmin.getQueueInfo(DOC_MESSAGE_UPDATE)).isNotNull();
            softly.assertThat(rabbitAdmin.getQueueInfo(DOC_MESSAGE_UPDATE).getName())
                    .isEqualTo(DOC_MESSAGE_UPDATE);
            softly.assertThat(rabbitAdmin.getQueueInfo(PHOTO_MESSAGE_UPDATE)).isNotNull();
            softly.assertThat(rabbitAdmin.getQueueInfo(PHOTO_MESSAGE_UPDATE).getName())
                    .isEqualTo(PHOTO_MESSAGE_UPDATE);
            softly.assertThat(rabbitAdmin.getQueueInfo(AUDIO_MESSAGE_UPDATE)).isNotNull();
            softly.assertThat(rabbitAdmin.getQueueInfo(AUDIO_MESSAGE_UPDATE).getName())
                    .isEqualTo(AUDIO_MESSAGE_UPDATE);
            softly.assertThat(rabbitAdmin.getQueueInfo(ANSWER_MESSAGE)).isNotNull();
            softly.assertThat(rabbitAdmin.getQueueInfo(ANSWER_MESSAGE).getName())
                    .isEqualTo(ANSWER_MESSAGE);
            softly.assertAll();
        });
    }

    @Test
    @DisplayName("Проверка загрузки локализации сообщения из файла")
    public void testMessageSourceLoaded() {
        String expectedMessage = "test";

        String actualMessage = messageSource.getMessage("reply.test", null, locale);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualMessage).isNotEmpty();
            softly.assertThat(actualMessage).isEqualTo(expectedMessage);
            softly.assertAll();
        });
    }
}