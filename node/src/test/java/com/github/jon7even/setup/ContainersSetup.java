package com.github.jon7even.setup;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Подготовка настроек тестконтейнера
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote использует современные аннотации, которые не требуют явного указывания секретов
 */
@Testcontainers
public class ContainersSetup extends GenericTests {

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16.2-alpine3.19");

    @Container
    @ServiceConnection
    public static RabbitMQContainer rabbitMQ =
            new RabbitMQContainer("rabbitmq:3.11.9-management-alpine");
}