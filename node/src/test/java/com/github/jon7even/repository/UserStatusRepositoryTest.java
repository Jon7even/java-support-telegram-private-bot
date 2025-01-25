package com.github.jon7even.repository;

import com.github.jon7even.entity.UserStatusEntity;
import com.github.jon7even.telegram.BotState;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Тестирование методов репозитория {@link UserStatusRepository}
 *
 * @author Jon7even
 * @version 2.0
 */
@DisplayName("Тестирование методов репозитория UserStatusRepository")
public class UserStatusRepositoryTest extends GenericRepositoryTest {

    @Autowired
    protected UserStatusRepository userStatusRepository;

    @Test
    @DisplayName("Корректное сохранение статуса MAIN_START")
    public void save_WhenSavedWithMainStart_ReturnsCorrectStatus() {
        UserStatusEntity expectedStatus = UserStatusEntity.builder()
                .chatId(userInBaseOne.getChatId())
                .status(BotState.MAIN_START)
                .build();

        UserStatusEntity actualStatus = userStatusRepository.save(expectedStatus);

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(actualStatus)
                .isNotNull();
        softAssertions.assertThat(actualStatus.getChatId())
                .isEqualTo(expectedStatus.getChatId());
        softAssertions.assertThat(actualStatus.getStatus())
                .isEqualTo(expectedStatus.getStatus());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Корректное получение статуса MAIN_START")
    public void getById_WhenCalledByUserOne_ReturnsCorrectStatus() {
        Long chatId = userInBaseOne.getChatId();
        UserStatusEntity expectedStatus = UserStatusEntity.builder()
                .chatId(chatId)
                .status(BotState.MAIN_START)
                .build();
        userStatusRepository.save(expectedStatus);

        UserStatusEntity actualStatus = userStatusRepository.getById(chatId);

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(actualStatus)
                .isNotNull();
        softAssertions.assertThat(actualStatus.getChatId())
                .isEqualTo(expectedStatus.getChatId());
        softAssertions.assertThat(actualStatus.getStatus())
                .isEqualTo(expectedStatus.getStatus());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Сохранение трех разных статусов у разных пользователей, все они должны быть в БД")
    public void save_WhenCalledWithValidData_ReturnsThreeUserStatusEntities() {
        UserStatusEntity expectedStatusOne = UserStatusEntity.builder()
                .chatId(userInBaseOne.getChatId())
                .status(BotState.MAIN_START)
                .build();
        UserStatusEntity expectedStatusTwo = UserStatusEntity.builder()
                .chatId(userInBaseTwo.getChatId())
                .status(BotState.MAIN_HE
                .build();
        UserStatusEntity expectedStatusThree = UserStatusEntity.builder()
                .chatId(userInBaseThree.getChatId())
                .status(BotState.MAIN_CALLBACK)
                .build();

        userStatusRepository.save(expectedStatusOne);
        userStatusRepository.save(expectedStatusTwo);
        userStatusRepository.save(expectedStatusThree);

        List<UserStatusEntity> actualResult = userStatusRepository.findAll();

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(actualResult)
                .isNotNull();
        softAssertions.assertThat(actualResult)
                .isNotEmpty();
        softAssertions.assertThat(actualResult.size())
                .isEqualTo(3);
        softAssertions.assertThat(actualResult)
                .containsExactlyInAnyOrder(expectedStatusOne, expectedStatusTwo, expectedStatusThree);
        softAssertions.assertAll();
    }
}