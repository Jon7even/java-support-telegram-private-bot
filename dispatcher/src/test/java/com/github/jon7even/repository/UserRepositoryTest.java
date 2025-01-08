package com.github.jon7even.repository;

import com.github.jon7even.entity.user.UserEntity;
import com.github.jon7even.setup.ContainersSetup;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

/**
 * Тестирование репозитория {@link UserRepository}
 *
 * @author Jon7even
 * @version 2.0
 */
@DataJpaTest
@ActiveProfiles(value = "test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Тестирование методов репозитория UserRepository")
public class UserRepositoryTest extends ContainersSetup {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUpEntity() {
        initUserEntity();
    }

    @Test
    @DisplayName("Корректное сохранение трех сущностей пользователя")
    public void save_WhenCalledWithValidData_ReturnsThreeUserEntities() {
        userRepository.save(userEntityOne);
        userRepository.save(userEntityTwo);
        userRepository.save(userEntityThree);

        List<UserEntity> actualResult = userRepository.findAll();

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(actualResult)
                .isNotNull();
        softAssertions.assertThat(actualResult)
                .isNotEmpty();
        softAssertions.assertThat(actualResult.size())
                .isEqualTo(3);
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Корректное сохранение сущности не аутентифицированного пользователя со всеми полями")
    public void save_WhenCalledWithValidData_ReturnsOneFullNameUser() {
        UserEntity actualUserOneFullName = userRepository.save(userEntityOne);

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(actualUserOneFullName)
                .isNotNull();
        softAssertions.assertThat(actualUserOneFullName.getId())
                .isEqualTo(1L);
        softAssertions.assertThat(actualUserOneFullName.getChatId())
                .isEqualTo(userEntityOne.getChatId());
        softAssertions.assertThat(actualUserOneFullName.getFirstName())
                .isEqualTo(userEntityOne.getFirstName());
        softAssertions.assertThat(actualUserOneFullName.getLastName())
                .isEqualTo(userEntityOne.getLastName());
        softAssertions.assertThat(actualUserOneFullName.getUserName())
                .isEqualTo(userEntityOne.getUserName());
        softAssertions.assertThat(actualUserOneFullName.getAuthorization())
                .isEqualTo(userEntityOne.getAuthorization());
        softAssertions.assertThat(actualUserOneFullName.getRegisteredOn())
                .isEqualTo(userEntityOne.getRegisteredOn());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Корректное сохранение сущности пользователя, если у него отсутствует имя фамилия и ник")
    public void save_WhenCalledWithValidData_ReturnsOneUserOfNullFieldsName() {
        UserEntity userTwoNull = userRepository.save(userEntityTwo);

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(userTwoNull)
                .isNotNull();
        softAssertions.assertThat(userTwoNull.getId())
                .isEqualTo(1L);
        softAssertions.assertThat(userTwoNull.getChatId())
                .isEqualTo(userEntityTwo.getChatId());
        softAssertions.assertThat(userTwoNull.getFirstName())
                .isEqualTo(userEntityTwo.getFirstName());
        softAssertions.assertThat(userTwoNull.getLastName())
                .isEqualTo(userEntityTwo.getLastName());
        softAssertions.assertThat(userTwoNull.getUserName())
                .isEqualTo(userEntityTwo.getUserName());
        softAssertions.assertThat(userTwoNull.getAuthorization())
                .isEqualTo(userEntityTwo.getAuthorization());
        softAssertions.assertThat(userTwoNull.getRegisteredOn())
                .isEqualTo(userEntityTwo.getRegisteredOn());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Корректное сохранение сущности пользователя со всеми полями и аутентификацией")
    public void save_WhenCalledWithValidData_ReturnsOneFullNameUserWitchAuthIsTrue() {
        UserEntity userThreeAuthOn = userRepository.save(userEntityThree);

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(userThreeAuthOn)
                .isNotNull();
        softAssertions.assertThat(userThreeAuthOn.getId())
                .isEqualTo(1L);
        softAssertions.assertThat(userThreeAuthOn.getChatId())
                .isEqualTo(userEntityThree.getChatId());
        softAssertions.assertThat(userThreeAuthOn.getFirstName())
                .isEqualTo(userEntityThree.getFirstName());
        softAssertions.assertThat(userThreeAuthOn.getLastName())
                .isEqualTo(userEntityThree.getLastName());
        softAssertions.assertThat(userThreeAuthOn.getUserName())
                .isEqualTo(userEntityThree.getUserName());
        softAssertions.assertThat(userThreeAuthOn.getAuthorization())
                .isNotNull();
        softAssertions.assertThat(userThreeAuthOn.getAuthorization())
                .isEqualTo(userEntityThree.getAuthorization());
        softAssertions.assertThat(userThreeAuthOn.getRegisteredOn())
                .isEqualTo(userEntityThree.getRegisteredOn());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Должен вернуть true, если существует пользователь с таким Telegram ID")
    public void existsByChatId_WhenCalledWithValidData_ReturnsTrue() {
        UserEntity userOneFullName = userRepository.save(userEntityOne);
        var validId = userOneFullName.getChatId();

        Boolean actualResult = userRepository.existsByChatId(validId);

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(actualResult)
                .isTrue();
        softAssertions.assertThat(validId)
                .isEqualTo(userEntityOne.getChatId());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Должен вернуть false, если пользователя с таким Telegram ID нет")
    public void existsByChatId_WhenCalledWithValidData_ReturnsFalse() {
        UserEntity userOneFullName = userRepository.save(userEntityOne);
        var notValidId = userOneFullName.getChatId() + 1L;

        Boolean actualResult = userRepository.existsByChatId(notValidId);

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(actualResult)
                .isFalse();
        softAssertions.assertThat(notValidId)
                .isNotEqualTo(userEntityOne.getChatId());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Должен найти пользователя, если существует пользователь с таким Telegram ID")
    public void findByChatId_WhenCalledWithValidData_ReturnsOptionalIsPresent() {
        UserEntity userOneFullName = userRepository.save(userEntityOne);
        var validId = userOneFullName.getChatId();

        Optional<UserEntity> actualResult = userRepository.findByChatId(validId);

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(actualResult)
                .isPresent();
        softAssertions.assertThat(actualResult.get().getChatId())
                .isEqualTo(userEntityOne.getChatId());
        softAssertions.assertThat(validId)
                .isEqualTo(userEntityOne.getChatId());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Не должен найти пользователя, если пользователя с таким Telegram ID нет")
    public void findByChatId_WhenCalledWithValidData_ReturnsOptionalIsNull() {
        UserEntity userOneFullName = userRepository.save(userEntityOne);
        var notValidId = userOneFullName.getChatId() + 1L;

        Optional<UserEntity> actualResult = userRepository.findByChatId(notValidId);

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(actualResult)
                .isNotPresent();
        softAssertions.assertThat(notValidId)
                .isNotEqualTo(userEntityOne.getChatId());
        softAssertions.assertAll();
    }
}