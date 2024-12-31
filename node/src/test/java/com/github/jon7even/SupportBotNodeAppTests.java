package com.github.jon7even;

import com.github.jon7even.setup.ContainersSetup;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
@ActiveProfiles(value = "test")
@SpringBootTest(classes = NodeApp.class)
@DisplayName("Тестирование запуска сервиса NodeApp")
class SupportBotNodeAppTests extends ContainersSetup {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Test
    @DisplayName("Проверка загрузки контекста приложения")
    void contextLoads() {
    }

    @Test
    @DisplayName("Проверка загрузки приложения без исключений")
    void testMain() {
        assertThatCode(NodeApp::new).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Проверка создания очередей RabbitMq")
    void testQueuesExist() {
        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(rabbitAdmin.getQueueInfo(TEXT_MESSAGE_UPDATE))
                .isNotNull();
        softAssertions.assertThat(rabbitAdmin.getQueueInfo(CALLBACK_QUERY_UPDATE))
                .isNotNull();
        softAssertions.assertThat(rabbitAdmin.getQueueInfo(DOC_MESSAGE_UPDATE))
                .isNotNull();
        softAssertions.assertThat(rabbitAdmin.getQueueInfo(PHOTO_MESSAGE_UPDATE))
                .isNotNull();
        softAssertions.assertThat(rabbitAdmin.getQueueInfo(AUDIO_MESSAGE_UPDATE))
                .isNotNull();
        softAssertions.assertThat(rabbitAdmin.getQueueInfo(ANSWER_MESSAGE))
                .isNotNull();

        softAssertions.assertAll();
    }
}