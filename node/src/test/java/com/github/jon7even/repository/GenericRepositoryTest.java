package com.github.jon7even.repository;

import com.github.jon7even.NodeApp;
import com.github.jon7even.entity.user.UserEntity;
import com.github.jon7even.setup.GenericTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "test")
@SpringBootTest(classes = NodeApp.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GenericRepositoryTest extends GenericTests {
    @Autowired
    protected UserRepository userRepository;

    protected UserEntity userInBaseOne;
    protected UserEntity userInBaseSecond;
    protected UserEntity userInBaseThird;

    @BeforeEach
    void addUser() {
        initUsers();
        userInBaseOne = userRepository.save(userEntityOne);
        userInBaseSecond = userRepository.save(userEntitySecond);
        userInBaseThird = userRepository.save(userEntityThird);
    }

    @AfterEach
    void clearRepository() {
        userRepository.deleteAll();
    }
}
