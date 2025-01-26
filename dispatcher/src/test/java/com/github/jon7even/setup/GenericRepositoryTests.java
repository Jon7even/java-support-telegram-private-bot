package com.github.jon7even.setup;

import com.github.jon7even.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Компоновка и настройка необходимых конфигураций для тестов репозиториев.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote использует современные аннотации, которые не требуют явного указывания секретов.
 */
@DataJpaTest
@Testcontainers
@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
@ImportTestcontainers(ContainerPostgreSQL.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GenericRepositoryTests extends TestDataFactory {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void setUpEntity() {
        initUserEntity();
    }
}