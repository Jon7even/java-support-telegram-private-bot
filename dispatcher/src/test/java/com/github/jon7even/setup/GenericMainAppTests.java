package com.github.jon7even.setup;

import com.github.jon7even.DispatcherApp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Компоновка и настройка необходимых конфигураций для тестирования запуска микросервиса {@link DispatcherApp}
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Использует современные аннотации, которые не требуют явного указывания секретов и настроек.
 */
@Testcontainers
@ActiveProfiles(value = "test")
@SpringBootTest(classes = DispatcherApp.class)
@ImportTestcontainers({ContainerPostgreSQL.class, ContainerRabbitMQ.class})
public class GenericMainAppTests {
}