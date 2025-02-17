package com.github.jon7even.configuration;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Тестирование загрузки конфигурации {@link BotConfig}
 *
 * @author Jon7even
 * @version 2.0
 */
@DisplayName("Тестирование загрузки конфигурации BotConfig")
@EnableConfigurationProperties(BotConfig.class)
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "bot.token.name=testBotName",
        "bot.token.token=testBotToken123"
})
public class BotConfigTest {

    @Autowired
    private BotConfig botConfig;

    @Test
    @DisplayName("Успешная загрузка BotConfig")
    public void botConfigLoading_Success() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(botConfig.getName()).isEqualTo("testBotName");
            softly.assertThat(botConfig.getToken()).isEqualTo("testBotToken123");
            softly.assertAll();
        });
    }
}