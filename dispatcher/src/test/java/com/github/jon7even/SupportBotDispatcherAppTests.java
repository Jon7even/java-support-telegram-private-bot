package com.github.jon7even;

import com.github.jon7even.setup.ContainersSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Тестирование запуска контекста приложения без ошибок
 *
 * @author Jon7even
 * @version 2.0
 */
@DisplayName("Тестирование запуска приложения")
@ActiveProfiles(value = "test")
@SpringBootTest(classes = DispatcherApp.class)
class SupportBotDispatcherAppTests extends ContainersSetup {

    @Test
    void contextLoads() {
    }

    @Test
    void testMain() {
        Assertions.assertDoesNotThrow(DispatcherApp::new);
    }
}