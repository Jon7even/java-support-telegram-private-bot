package com.github.jon7even.mapper;

import com.github.jon7even.entity.UserStatusEntity;
import com.github.jon7even.telegram.BotState;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Тестирование маппера {@link UserStatusMapperImpl}
 *
 * @author Jon7even
 * @version 2.0
 */
@DisplayName("Тестирование методов маппера UserStatusMapperImpl")
public class UserStatusMapperTest {

    private UserStatusMapper userStatusMapper;

    @BeforeEach
    public void setUp() {
        userStatusMapper = new UserStatusMapperImpl();
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг в сущность UserStatusEntity из BotState и chatId")
    public void toUserStatusEntityFromChatIdAndStatus_ReturnsUserStatusEntity() {
        BotState botState = BotState.MAIN_ASK;
        Long chatId = 222222L;
        UserStatusEntity expectedEntity = UserStatusEntity.builder()
                .chatId(chatId)
                .status(botState)
                .build();

        UserStatusEntity actualEntity = userStatusMapper.toUserStatusEntityFromChatIdAndStatus(chatId, botState);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actualEntity)
                .isNotNull();
        softAssertions.assertThat(actualEntity)
                .isEqualTo(expectedEntity);
        softAssertions.assertThat(actualEntity.getChatId())
                .isEqualTo(expectedEntity.getChatId());
        softAssertions.assertThat(actualEntity.getStatus())
                .isEqualTo(expectedEntity.getStatus());
        softAssertions.assertAll();
    }
}