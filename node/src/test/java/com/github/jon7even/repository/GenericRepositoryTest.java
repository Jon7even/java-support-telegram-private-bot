package com.github.jon7even.repository;

import com.github.jon7even.entity.user.UserEntity;
import com.github.jon7even.setup.ContainersSetup;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

/**
 * Подготовка необходимых данных для тестирования репозиториев {@link UserRepository}
 *
 * @author Jon7even
 * @version 2.0
 */
@DataJpaTest
@ActiveProfiles(value = "test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GenericRepositoryTest extends ContainersSetup {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUpEntity() {
        initUserEntity();
    }

    protected UserEntity userInBaseOne;
    protected UserEntity userInBaseSecond;
    protected UserEntity userInBaseThird;

    @BeforeEach
    public void addUser() {
        initUserEntity();
        userInBaseOne = userRepository.save(userEntityOne);
        userInBaseSecond = userRepository.save(userEntityTwo);
        userInBaseThird = userRepository.save(userEntityThree);
    }
}