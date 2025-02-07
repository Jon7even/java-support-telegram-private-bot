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
 * Тестирование загрузки конфигурации {@link NodeApiAskConfig}
 *
 * @author Jon7even
 * @version 2.0
 */
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(NodeApiAskConfig.class)
@TestPropertySource(properties = {
        "node.api-ask.url=https://example-api.com",
        "node.api-ask.timeout-connect=5000",
        "node.api-ask.timeout-response=10000",
        "node.api-ask.timeout-read=5000",
        "node.api-ask.timeout-pool=5000",
        "node.api-ask.max-connections-pool=7"
})
@DisplayName("Тестирование загрузки конфигурации NodeApiAskConfig")
public class NodeApiAskConfigTest {

    @Autowired
    private NodeApiAskConfig nodeApiAskConfig;

    @Test
    @DisplayName("Успешная загрузка NodeApiAskConfig")
    public void nodeApiAskConfigLoading_Success() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(nodeApiAskConfig.getUrl()).isEqualTo("https://example-api.com");
            softly.assertThat(nodeApiAskConfig.getTimeoutConnect()).isEqualTo(5000);
            softly.assertThat(nodeApiAskConfig.getTimeoutResponse()).isEqualTo(10000);
            softly.assertThat(nodeApiAskConfig.getTimeoutRead()).isEqualTo(5000);
            softly.assertThat(nodeApiAskConfig.getTimeoutPool()).isEqualTo(5000);
            softly.assertThat(nodeApiAskConfig.getMaxConnectionsPool()).isEqualTo(7);
            softly.assertAll();
        });
    }
}