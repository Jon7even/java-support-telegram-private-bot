package com.github.jon7even.setup;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

/**
 * Интерфейс, который объявляет и настраивает контейнер с реляционной базой данных PostgreSQL.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Включите его через аннотацию {@code @ImportTestcontainers}, там где он требуется.
 */
public interface ContainerPostgreSQL {

    @Container
    @ServiceConnection
    PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.6-alpine3.21");
}