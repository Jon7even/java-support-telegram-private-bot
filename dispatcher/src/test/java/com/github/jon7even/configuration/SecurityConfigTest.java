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
 * Тестирование загрузки конфигурации {@link SecurityConfig}
 *
 * @author Jon7even
 * @version 2.0
 */

@DisplayName("Тестирование загрузки конфигурации SecurityConfig")
public class SecurityConfigTest {

    @InjectMocks
    private SecurityConfig securityConfig;

    @Mock
    private SecurityConfig mockSecurityConfig;

    public SecurityConfigTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBotConfigLoading() {
        when(mockSecurityConfig.getKeyPass()).thenReturn("testPass");
        when(mockSecurityConfig.getAttemptsAuth()).thenReturn(777);

        SoftAssertions softAssertions = new SoftAssertions();
        assertThat(mockSecurityConfig.getKeyPass())
                .isEqualTo("testPass");
        assertThat(mockSecurityConfig.getAttemptsAuth())
                .isEqualTo(777);
        softAssertions.assertAll();
    }
}