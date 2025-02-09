package com.github.jon7even.mapper;

import com.github.jon7even.dto.UserAuthFalseDto;
import com.github.jon7even.dto.UserAuthTrueDto;
import com.github.jon7even.dto.UserCreateDto;
import com.github.jon7even.dto.UserUpdateDto;
import com.github.jon7even.entity.user.UserEntity;
import com.github.jon7even.setup.TestDataFactory;
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
public class UserMapperTest extends TestDataFactory {

    private final UserMapper userMapper = new UserMapperImpl();

    @BeforeEach
    public void setUp() {
        initUserEntity();
        initUserDto();
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг UserCreateDto в UserEntity для сохранения нового юзера в БД")
    public void toEntityFromCreateDto_ReturnsUserEntityWithNotId() {
        UserEntity actualResult = userMapper.toEntityFromCreateDto(userCreateDtoOne);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualResult).isNotNull();
            softly.assertThat(actualResult.getId()).isNull();
            softly.assertThat(actualResult.getChatId()).isNotNull();
            softly.assertThat(actualResult.getChatId()).isEqualTo(userCreateDtoOne.getChatId());
            softly.assertThat(actualResult.getFirstName()).isNotNull();
            softly.assertThat(actualResult.getFirstName()).isEqualTo(userCreateDtoOne.getFirstName());
            softly.assertThat(actualResult.getLastName()).isNotNull();
            softly.assertThat(actualResult.getLastName()).isEqualTo(userCreateDtoOne.getLastName());
            softly.assertThat(actualResult.getUserName()).isNotNull();
            softly.assertThat(actualResult.getUserName()).isEqualTo(userCreateDtoOne.getUserName());
            softly.assertAll();
        });
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

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualResult).isNotNull();
            softly.assertThat(actualResult.getChatId()).isNotNull();
            softly.assertThat(actualResult.getChatId()).isEqualTo(expectedChatUser.getId());
            softly.assertThat(actualResult.getFirstName()).isNotNull();
            softly.assertThat(actualResult.getFirstName()).isEqualTo(expectedChatUser.getFirstName());
            softly.assertThat(actualResult.getLastName()).isNotNull();
            softly.assertThat(actualResult.getLastName()).isEqualTo(expectedChatUser.getLastName());
            softly.assertThat(actualResult.getUserName()).isNotNull();
            softly.assertThat(actualResult.getUserName()).isEqualTo(expectedChatUser.getUserName());
            softly.assertAll();
        });
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

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualResult).isNotNull();
            softly.assertThat(actualResult.getChatId()).isNotNull();
            softly.assertThat(actualResult.getChatId()).isEqualTo(expectedChatUser.getId());
            softly.assertThat(actualResult.getFirstName()).isNotNull();
            softly.assertThat(actualResult.getFirstName()).isEqualTo(expectedChatUser.getFirstName());
            softly.assertThat(actualResult.getLastName()).isNotNull();
            softly.assertThat(actualResult.getLastName()).isEqualTo(expectedChatUser.getLastName());
            softly.assertThat(actualResult.getUserName()).isNotNull();
            softly.assertThat(actualResult.getUserName()).isEqualTo(expectedChatUser.getUserName());
            softly.assertAll();
        });
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг UserEntity в UserAuthFalseDto")
    public void toAuthFalseDtoFromEntity_ReturnsUserAuthFalseDto() {
        UserEntity expectedUser = UserEntity.builder()
                .id(1L)
                .chatId(userEntityOne.getChatId())
                .firstName(userEntityOne.getFirstName())
                .lastName(userEntityOne.getLastName())
                .userName(userEntityOne.getUserName())
                .registeredOn(userEntityOne.getRegisteredOn())
                .build();

        UserAuthFalseDto actualResult = userMapper.toAuthFalseDtoFromEntity(expectedUser);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualResult).isNotNull();
            softly.assertThat(actualResult.getId()).isNotNull();
            softly.assertThat(actualResult.getId()).isEqualTo(expectedUser.getId());
            softly.assertThat(actualResult.getChatId()).isNotNull();
            softly.assertThat(actualResult.getChatId()).isEqualTo(expectedUser.getChatId());
            softly.assertThat(actualResult.getFirstName()).isNotNull();
            softly.assertThat(actualResult.getFirstName()).isEqualTo(expectedUser.getFirstName());
            softly.assertThat(actualResult.getLastName()).isNotNull();
            softly.assertThat(actualResult.getLastName()).isEqualTo(expectedUser.getLastName());
            softly.assertThat(actualResult.getUserName()).isNotNull();
            softly.assertThat(actualResult.getUserName()).isEqualTo(expectedUser.getUserName());
            softly.assertThat(actualResult.getAttemptAuth()).isNotNull();
            softly.assertThat(actualResult.getAttemptAuth()).isEqualTo(0L);
            softly.assertAll();
        });
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг UserEntity в UserAuthTrueDto")
    public void toAuthTrueDtoFromEntity_ReturnsUserAuthTrueDto() {
        UserEntity expectedUser = UserEntity.builder()
                .id(1L)
                .chatId(userEntityOne.getChatId())
                .firstName(userEntityOne.getFirstName())
                .lastName(userEntityOne.getLastName())
                .userName(userEntityOne.getUserName())
                .registeredOn(userEntityOne.getRegisteredOn())
                .build();

        UserAuthTrueDto actualResult = userMapper.toAuthTrueDtoFromEntity(expectedUser);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualResult).isNotNull();
            softly.assertThat(actualResult.getId()).isNotNull();
            softly.assertThat(actualResult.getId()).isEqualTo(expectedUser.getId());
            softly.assertThat(actualResult.getChatId()).isNotNull();
            softly.assertThat(actualResult.getChatId()).isEqualTo(expectedUser.getChatId());
            softly.assertThat(actualResult.getFirstName()).isNotNull();
            softly.assertThat(actualResult.getFirstName()).isEqualTo(expectedUser.getFirstName());
            softly.assertThat(actualResult.getLastName()).isNotNull();
            softly.assertThat(actualResult.getLastName()).isEqualTo(expectedUser.getLastName());
            softly.assertThat(actualResult.getUserName()).isNotNull();
            softly.assertThat(actualResult.getUserName()).isEqualTo(expectedUser.getUserName());
            softly.assertAll();
        });
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг обновления UserEntity из полей UserUpdateDto")
    public void updateUserEntityFromDtoUpdate_updatedUserEntity() {
        UserEntity actualUserFromUpdate = UserEntity.builder()
                .id(1L)
                .chatId(userEntityOne.getChatId())
                .firstName(userEntityOne.getFirstName())
                .lastName(userEntityOne.getLastName())
                .userName(userEntityOne.getUserName())
                .authorization(false)
                .registeredOn(userEntityOne.getRegisteredOn())
                .build();

        Long notValidTelegramId = 0L;

        UserUpdateDto expectedUserUpdateDto = UserUpdateDto.builder()
                .chatId(notValidTelegramId)
                .firstName("Updated FirstName")
                .lastName("Updated FirstLastName")
                .userName("Updated FirstUserName")
                .build();

        userMapper.updateUserEntityFromDtoUpdate(actualUserFromUpdate, expectedUserUpdateDto, true);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualUserFromUpdate).isNotNull();
            softly.assertThat(actualUserFromUpdate.getId()).isNotNull();
            softly.assertThat(actualUserFromUpdate.getChatId()).isNotNull();
            softly.assertThat(actualUserFromUpdate.getChatId()).isNotEqualTo(notValidTelegramId);
            softly.assertThat(actualUserFromUpdate.getFirstName()).isNotNull();
            softly.assertThat(actualUserFromUpdate.getFirstName()).isEqualTo(expectedUserUpdateDto.getFirstName());
            softly.assertThat(actualUserFromUpdate.getLastName()).isNotNull();
            softly.assertThat(actualUserFromUpdate.getLastName()).isEqualTo(expectedUserUpdateDto.getLastName());
            softly.assertThat(actualUserFromUpdate.getUserName()).isNotNull();
            softly.assertThat(actualUserFromUpdate.getUserName()).isEqualTo(expectedUserUpdateDto.getUserName());
            softly.assertThat(actualUserFromUpdate.getAuthorization()).isNotNull();
            softly.assertThat(actualUserFromUpdate.getAuthorization()).isEqualTo(true);
            softly.assertThat(actualUserFromUpdate.getUpdatedOn()).isNotNull();
            softly.assertThat(actualUserFromUpdate.getUpdatedOn()).isBefore(LocalDateTime.now().plusMinutes(1));
            softly.assertAll();
        });
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг обновления UserEntity и установить поле авторизации на true")
    public void updateUserEntitySetAuthorizationTrue_updatedUserEntityIs() {
        UserEntity actualUserFromUpdate = UserEntity.builder()
                .id(1L)
                .chatId(userEntityOne.getChatId())
                .firstName(userEntityOne.getFirstName())
                .lastName(userEntityOne.getLastName())
                .userName(userEntityOne.getUserName())
                .authorization(false)
                .registeredOn(userEntityOne.getRegisteredOn())
                .build();

        UserEntity expectedUserAfterUpdate = UserEntity.builder()
                .id(1L)
                .chatId(userEntityOne.getChatId())
                .firstName(userEntityOne.getFirstName())
                .lastName(userEntityOne.getLastName())
                .userName(userEntityOne.getUserName())
                .authorization(true)
                .registeredOn(userEntityOne.getRegisteredOn())
                .build();

        userMapper.updateUserEntitySetAuthorizationIsTrue(actualUserFromUpdate);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualUserFromUpdate).isNotNull();
            softly.assertThat(actualUserFromUpdate.getId()).isNotNull();
            softly.assertThat(actualUserFromUpdate.getId()).isEqualTo(expectedUserAfterUpdate.getId());
            softly.assertThat(actualUserFromUpdate.getChatId()).isNotNull();
            softly.assertThat(actualUserFromUpdate.getChatId()).isEqualTo(expectedUserAfterUpdate.getChatId());
            softly.assertThat(actualUserFromUpdate.getFirstName()).isNotNull();
            softly.assertThat(actualUserFromUpdate.getFirstName()).isEqualTo(expectedUserAfterUpdate.getFirstName());
            softly.assertThat(actualUserFromUpdate.getLastName()).isNotNull();
            softly.assertThat(actualUserFromUpdate.getLastName()).isEqualTo(expectedUserAfterUpdate.getLastName());
            softly.assertThat(actualUserFromUpdate.getUserName()).isNotNull();
            softly.assertThat(actualUserFromUpdate.getUserName()).isEqualTo(expectedUserAfterUpdate.getUserName());
            softly.assertThat(actualUserFromUpdate.getAuthorization()).isNotNull();
            softly.assertThat(actualUserFromUpdate.getAuthorization())
                    .isEqualTo(expectedUserAfterUpdate.getAuthorization());
            softly.assertThat(actualUserFromUpdate.getUpdatedOn()).isNotNull();
            softly.assertThat(actualUserFromUpdate.getUpdatedOn()).isBefore(LocalDateTime.now().plusMinutes(1));
            softly.assertAll();
        });
    }
}