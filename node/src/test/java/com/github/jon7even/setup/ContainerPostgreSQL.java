package com.github.jon7even.setup;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
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
    PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.6-alpine3.21");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}