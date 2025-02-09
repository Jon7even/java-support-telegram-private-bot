package com.github.jon7even.mapper;

import com.github.jon7even.entity.UserStatusEntity;
import com.github.jon7even.setup.TestDataFactory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.jon7even.telegram.BotState.MAIN_ASK;

/**
 * Тестирование маппера {@link UserStatusMapperImpl}
 *
 * @author Jon7even
 * @version 2.0
 */
@DisplayName("Тестирование методов маппера UserStatusMapperImpl")
public class UserStatusMapperTest extends TestDataFactory {

    private final UserStatusMapper userStatusMapper = new UserStatusMapperImpl();

    @BeforeEach
    public void setUp() {
        initUserStatus();
    }

    @Test
    @DisplayName("Правильный маппинг в сущность UserStatusEntity из chatId и BotState")
    public void toUserStatusEntityFromChatIdAndStatus_ReturnsUserStatusEntity() {
        UserStatusEntity actualEntity = userStatusMapper.toUserStatusEntityFromChatIdAndStatus(chatIdOne, MAIN_ASK);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualEntity).isNotNull();
            softly.assertThat(actualEntity).isEqualTo(expectedStatusASK);
            softly.assertThat(actualEntity.getChatId()).isEqualTo(expectedStatusASK.getChatId());
            softly.assertThat(actualEntity.getStatus()).isEqualTo(expectedStatusASK.getStatus());
            softly.assertAll();
        });
    }

    @Test
    @DisplayName("Правильный маппинг в сущность UserStatusEntity со стандартным статусом MAIN_START из chatId")
    public void toDefaultUserStatusEntityFromChatId() {
        UserStatusEntity actualEntity = userStatusMapper.toDefaultUserStatusEntityFromChatId(chatIdTwo);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualEntity).isNotNull();
            softly.assertThat(actualEntity).isEqualTo(expectedStatusDefault);
            softly.assertThat(actualEntity.getChatId()).isEqualTo(expectedStatusDefault.getChatId());
            softly.assertThat(actualEntity.getStatus()).isEqualTo(expectedStatusDefault.getStatus());
            softly.assertAll();
        });
    }
}