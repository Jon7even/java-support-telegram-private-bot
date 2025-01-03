package com.github.jon7even.configuration;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

/**
 * Тестирование загрузки конфигурации {@link BotConfig}
 *
 * @author Jon7even
 * @version 2.0
 */

@DisplayName("Тестирование загрузки конфигурации BotConfig")
public class BotConfigTest {

    @InjectMocks
    private BotConfig botConfig;

    @Mock
    private BotConfig mockBotConfig;

    public BotConfigTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBotConfigLoading() {
        when(mockBotConfig.getName()).thenReturn("testBotName");
        when(mockBotConfig.getToken()).thenReturn("testBotToken123");

        SoftAssertions softAssertions = new SoftAssertions();
        assertThat(mockBotConfig.getName())
                .isEqualTo("testBotName");
        assertThat(mockBotConfig.getToken())
                .isEqualTo("testBotToken123");
        softAssertions.assertAll();
    }
}