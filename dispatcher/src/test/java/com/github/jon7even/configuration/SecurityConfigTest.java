package com.github.jon7even.configuration;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

/**
 * Тестирование загрузки конфигурации {@link SecurityConfig}
 *
 * @author Jon7even
 * @version 2.0
 */

@DisplayName("Тестирование загрузки конфигурации SecurityConfig")
@ExtendWith(MockitoExtension.class)
public class SecurityConfigTest {

    @InjectMocks
    private SecurityConfig securityConfig;

    @Mock
    private SecurityConfig mockSecurityConfig;

    @Test
    public void testBotConfigLoading() {
        when(mockSecurityConfig.getKeyPass()).thenReturn("testPass");
        when(mockSecurityConfig.getAttemptsAuth()).thenReturn(3);

        SoftAssertions softAssertions = new SoftAssertions();
        assertThat(mockSecurityConfig.getKeyPass())
                .isEqualTo("testPass");
        assertThat(mockSecurityConfig.getAttemptsAuth())
                .isEqualTo(3);
        softAssertions.assertAll();
    }
}