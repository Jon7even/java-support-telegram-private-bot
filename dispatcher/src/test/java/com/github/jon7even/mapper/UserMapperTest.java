package com.github.jon7even.mapper;

import com.github.jon7even.dto.UserCreateDto;
import com.github.jon7even.dto.UserUpdateDto;
import com.github.jon7even.entity.user.UserEntity;
import com.github.jon7even.setup.PreparationForTests;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

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
    @DisplayName("Должен произойти правильный маппинг UserCreateDto в UserEntity для сохранения нового юзера в БД")
    public void toEntityFromCreateDto_ReturnsUserEntityWithNotId() {
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

    @Test
    @DisplayName("Должен произойти правильный маппинг Chat в UserCreateDto")
    public void toDtoCreateFromMessage_ReturnsUserCreateDto() {
        Chat expectedChatUser = Chat.builder()
                .id(userIdOne)
                .firstName(userCreateDtoOne.getFirstName())
                .lastName(userCreateDtoOne.getLastName())
                .userName(userCreateDtoOne.getUserName())
                .isForum(false)
                .title("test")
                .type("private")
                .build();

        UserCreateDto actualResult = userMapper.toDtoCreateFromMessage(expectedChatUser);

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(actualResult)
                .isNotNull();
        softAssertions.assertThat(actualResult.getChatId())
                .isNotNull()
                .isEqualTo(expectedChatUser.getId());
        softAssertions.assertThat(actualResult.getFirstName())
                .isNotNull()
                .isEqualTo(expectedChatUser.getFirstName());
        softAssertions.assertThat(actualResult.getLastName())
                .isNotNull()
                .isEqualTo(expectedChatUser.getLastName());
        softAssertions.assertThat(actualResult.getUserName())
                .isNotNull()
                .isEqualTo(expectedChatUser.getUserName());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг Chat в UserUpdateDto")
    public void toDtoCreateFromMessage_ReturnsUserUpdateDto() {
        Chat expectedChatUser = Chat.builder()
                .id(userIdOne)
                .firstName(userCreateDtoOne.getFirstName())
                .lastName(userCreateDtoOne.getLastName())
                .userName(userCreateDtoOne.getUserName())
                .isForum(false)
                .title("test")
                .type("private")
                .build();

        UserUpdateDto actualResult = userMapper.toDtoUpdateFromMessage(expectedChatUser);

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(actualResult)
                .isNotNull();
        softAssertions.assertThat(actualResult.getChatId())
                .isNotNull()
                .isEqualTo(expectedChatUser.getId());
        softAssertions.assertThat(actualResult.getFirstName())
                .isNotNull()
                .isEqualTo(expectedChatUser.getFirstName());
        softAssertions.assertThat(actualResult.getLastName())
                .isNotNull()
                .isEqualTo(expectedChatUser.getLastName());
        softAssertions.assertThat(actualResult.getUserName())
                .isNotNull()
                .isEqualTo(expectedChatUser.getUserName());
        softAssertions.assertAll();
    }
}