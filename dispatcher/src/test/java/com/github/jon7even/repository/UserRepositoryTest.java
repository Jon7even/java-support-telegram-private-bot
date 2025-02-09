package com.github.jon7even.repository;

import com.github.jon7even.entity.user.UserEntity;
import com.github.jon7even.setup.GenericRepositoryTests;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

/**
 * Тестирование методов репозитория {@link UserRepository}
 *
 * @author Jon7even
 * @version 2.0
 */
@DisplayName("Тестирование методов репозитория UserRepository")
public class UserRepositoryTest extends GenericRepositoryTests {

    @Test
    @DisplayName("Корректное сохранение трех сущностей пользователя")
    public void save_WhenCalledWithValidData_ReturnsThreeUserEntities() {
        userRepository.save(userEntityOne);
        userRepository.save(userEntityTwo);
        userRepository.save(userEntityThree);
        int expectedSizeOfList = 3;

        List<UserEntity> actualResult = userRepository.findAll();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualResult).isNotNull();
            softly.assertThat(actualResult).isNotEmpty();
            softly.assertThat(actualResult.size()).isEqualTo(expectedSizeOfList);
            softly.assertAll();
        });
    }

    @Test
    @DisplayName("Корректное сохранение сущности не аутентифицированного пользователя со всеми полями")
    public void save_WhenCalledWithValidData_ReturnsOneFullNameUser() {
        UserEntity actualUserOneFullName = userRepository.save(userEntityOne);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualUserOneFullName).isNotNull();
            softly.assertThat(actualUserOneFullName.getId()).isEqualTo(userIdOne);
            softly.assertThat(actualUserOneFullName.getChatId()).isEqualTo(userEntityOne.getChatId());
            softly.assertThat(actualUserOneFullName.getFirstName()).isEqualTo(userEntityOne.getFirstName());
            softly.assertThat(actualUserOneFullName.getLastName()).isEqualTo(userEntityOne.getLastName());
            softly.assertThat(actualUserOneFullName.getUserName()).isEqualTo(userEntityOne.getUserName());
            softly.assertThat(actualUserOneFullName.getAuthorization()).isEqualTo(userEntityOne.getAuthorization());
            softly.assertThat(actualUserOneFullName.getRegisteredOn()).isEqualTo(userEntityOne.getRegisteredOn());
            softly.assertAll();
        });
    }

    @Test
    @DisplayName("Корректное сохранение сущности пользователя, если у него отсутствует имя фамилия и ник")
    public void save_WhenCalledWithValidData_ReturnsOneUserOfNullFieldsName() {
        UserEntity userTwoNull = userRepository.save(userEntityTwo);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(userTwoNull).isNotNull();
            softly.assertThat(userTwoNull.getId()).isEqualTo(userIdOne);
            softly.assertThat(userTwoNull.getChatId()).isEqualTo(userEntityTwo.getChatId());
            softly.assertThat(userTwoNull.getFirstName()).isEqualTo(userEntityTwo.getFirstName());
            softly.assertThat(userTwoNull.getLastName()).isEqualTo(userEntityTwo.getLastName());
            softly.assertThat(userTwoNull.getUserName()).isEqualTo(userEntityTwo.getUserName());
            softly.assertThat(userTwoNull.getAuthorization()).isEqualTo(userEntityTwo.getAuthorization());
            softly.assertThat(userTwoNull.getRegisteredOn()).isEqualTo(userEntityTwo.getRegisteredOn());
            softly.assertAll();
        });
    }

    @Test
    @DisplayName("Корректное сохранение сущности пользователя со всеми полями и аутентификацией")
    public void save_WhenCalledWithValidData_ReturnsOneFullNameUserWitchAuthIsTrue() {
        UserEntity userThreeAuthOn = userRepository.save(userEntityThree);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(userThreeAuthOn).isNotNull();
            softly.assertThat(userThreeAuthOn.getId()).isEqualTo(userIdOne);
            softly.assertThat(userThreeAuthOn.getChatId()).isEqualTo(userEntityThree.getChatId());
            softly.assertThat(userThreeAuthOn.getFirstName()).isEqualTo(userEntityThree.getFirstName());
            softly.assertThat(userThreeAuthOn.getLastName()).isEqualTo(userEntityThree.getLastName());
            softly.assertThat(userThreeAuthOn.getUserName()).isEqualTo(userEntityThree.getUserName());
            softly.assertThat(userThreeAuthOn.getAuthorization()).isNotNull();
            softly.assertThat(userThreeAuthOn.getAuthorization()).isEqualTo(userEntityThree.getAuthorization());
            softly.assertThat(userThreeAuthOn.getRegisteredOn()).isEqualTo(userEntityThree.getRegisteredOn());
            softly.assertAll();
        });
    }

    @Test
    @DisplayName("Должен вернуть true, если существует пользователь с таким Telegram ID")
    public void existsByChatId_WhenCalledWithValidData_ReturnsTrue() {
        UserEntity userOneFullName = userRepository.save(userEntityOne);
        var validId = userOneFullName.getChatId();

        Boolean actualResult = userRepository.existsByChatId(validId);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualResult).isTrue();
            softly.assertThat(validId).isEqualTo(userEntityOne.getChatId());
            softly.assertAll();
        });
    }

    @Test
    @DisplayName("Должен вернуть false, если пользователя с таким Telegram ID нет")
    public void existsByChatId_WhenCalledWithValidData_ReturnsFalse() {
        UserEntity userOneFullName = userRepository.save(userEntityOne);
        var notValidId = userOneFullName.getChatId() + 1L;

        Boolean actualResult = userRepository.existsByChatId(notValidId);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualResult).isFalse();
            softly.assertThat(notValidId).isNotEqualTo(userEntityOne.getChatId());
            softly.assertAll();
        });
    }

    @Test
    @DisplayName("Должен найти пользователя, если существует пользователь с таким Telegram ID")
    public void findByChatId_WhenCalledWithValidData_ReturnsOptionalIsPresent() {
        UserEntity userOneFullName = userRepository.save(userEntityOne);
        var validId = userOneFullName.getChatId();

        Optional<UserEntity> actualResult = userRepository.findByChatId(validId);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualResult).isPresent();
            softly.assertThat(actualResult.get().getChatId()).isEqualTo(userEntityOne.getChatId());
            softly.assertThat(validId).isEqualTo(userEntityOne.getChatId());
            softly.assertAll();
        });
    }

    @Test
    @DisplayName("Не должен найти пользователя, если пользователя с таким Telegram ID нет")
    public void findByChatId_WhenCalledWithValidData_ReturnsOptionalIsNull() {
        UserEntity userOneFullName = userRepository.save(userEntityOne);
        var notValidId = userOneFullName.getChatId() + 1L;

        Optional<UserEntity> actualResult = userRepository.findByChatId(notValidId);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualResult).isNotPresent();
            softly.assertThat(notValidId).isNotEqualTo(userEntityOne.getChatId());
            softly.assertAll();
        });
    }
}