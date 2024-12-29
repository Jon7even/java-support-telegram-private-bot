package com.github.jon7even;

import com.github.jon7even.setup.ContainersSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Проверка запуска без ошибок контекста приложения {@link DispatcherApp}
 *
 * @author Jon7even
 * @version 2.0
 */
@ActiveProfiles(value = "test")
@SpringBootTest(classes = DispatcherApp.class)
@DisplayName("Тестирование запуска сервиса DispatcherApp")
class SupportBotDispatcherAppTests extends ContainersSetup {

    @Test
    void contextLoads() {
    }

    @Test
    void testMain() {
        Assertions.assertDoesNotThrow(DispatcherApp::new);
    }
}