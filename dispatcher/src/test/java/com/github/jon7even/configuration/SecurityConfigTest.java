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
 * Тестирование загрузки конфигурации {@link SecurityConfig}
 *
 * @author Jon7even
 * @version 2.0
 */

@DisplayName("Тестирование загрузки конфигурации SecurityConfig")
@EnableConfigurationProperties(SecurityConfig.class)
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "bot.security.keyPass=testPass",
        "bot.security.attemptsAuth=3"
})
public class SecurityConfigTest {

    @Autowired
    private SecurityConfig securityConfig;

    @Test
    @DisplayName("Успешная загрузка SecurityConfig")
    public void securityConfigLoading_Success() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(securityConfig.getKeyPass()).isEqualTo("testPass");
            softly.assertThat(securityConfig.getAttemptsAuth()).isEqualTo(3);
            softly.assertAll();
        });
    }
}