package com.github.jon7even.mapper;

import com.github.jon7even.entity.user.UserEntity;
import com.github.jon7even.setup.PreparationForTests;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * Тестирование маппера {@link UserMapperImpl}
 *
 * @author Jon7even
 * @version 2.0
 */
@DisplayName("Тестирование методов маппера UserMapperImpl")
public class UserMapperTest extends PreparationForTests {

    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapperImpl();
        initUserEntity();
        initUserDto();
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг в сущность для создания новых данных в БД")
    public void toEntityFromDtoCreate_ReturnEntityNotId() {

        UserEntity actualResult = userMapper.toEntityFromCreateDto(userCreateDtoOne, LocalDateTime.now());

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(actualResult)
                .isNotNull();
        softAssertions.assertThat(actualResult.getId())
                .isNull();
        softAssertions.assertThat(actualResult.getChatId())
                .isNotNull()
                .isEqualTo(userCreateDtoOne.getChatId());
        softAssertions.assertThat(actualResult.getFirstName())
                .isNotNull()
                .isEqualTo(userCreateDtoOne.getFirstName());
        softAssertions.assertThat(actualResult.getLastName())
                .isNotNull()
                .isEqualTo(userCreateDtoOne.getLastName());
        softAssertions.assertThat(actualResult.getUserName())
                .isNotNull()
                .isEqualTo(userCreateDtoOne.getUserName());
        softAssertions.assertAll();
    }
}