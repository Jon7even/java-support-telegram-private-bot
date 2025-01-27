package com.github.jon7even.setup;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;

/**
 * Интерфейс, который объявляет и настраивает контейнер с брокером очередей RabbitMQ.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Включите его через аннотацию {@code @ImportTestcontainers}, там где он требуется.
 */
public interface ContainerRabbitMQ {

    @Container
    @ServiceConnection
    RabbitMQContainer rabbitMQ = new RabbitMQContainer("rabbitmq:3.11.9-management-alpine");
}
