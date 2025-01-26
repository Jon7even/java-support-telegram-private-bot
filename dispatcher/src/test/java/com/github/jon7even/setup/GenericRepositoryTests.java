package com.github.jon7even.setup;

import com.github.jon7even.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Компоновка необходимых конфигураций для тестов репозиториев.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote использует современные аннотации, которые не требуют явного указывания секретов
 */
@DataJpaTest
@Testcontainers
@ActiveProfiles(value = "test")
@ImportTestcontainers(ContainerPostgreSQL.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ContainersSetup extends TestDataFactory {

    @Autowired
    protected UserRepository userRepository;

    @BeforeEach
    public void setUpEntity() {
        initUserEntity();
    }
}