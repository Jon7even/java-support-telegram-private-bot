package com.github.jon7even.service.in.status.impl;

import com.github.jon7even.entity.UserStatusEntity;
import com.github.jon7even.mapper.UserStatusMapper;
import com.github.jon7even.repository.UserStatusRepository;
import com.github.jon7even.setup.TestDataFactory;
import com.github.jon7even.telegram.BotState;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.github.jon7even.telegram.BotState.MAIN_ASK;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тестирование сервиса отвечающего за статус бота для пользователей {@link UserStatusServiceImpl}
 *
 * @author Jon7even
 * @version 2.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование методов сервиса UserStatusServiceImpl")
public class UserStatusServiceImplTest extends TestDataFactory {

    @InjectMocks
    private UserStatusServiceImpl userStatusService;

    @Mock
    private UserStatusRepository userStatusRepository;

    @Mock
    private UserStatusMapper userStatusMapper;

    @BeforeEach
    public void setUp() {
        initUserStatus();
    }

    @Test
    @DisplayName("Должен установить статус MAIN_ASK пользователю")
    public void setBotStateForUser_Success() {
        when(userStatusMapper.toUserStatusEntityFromChatIdAndStatus(chatIdOne, MAIN_ASK)).thenReturn(expectedStatusASK);
        when(userStatusRepository.save(any(UserStatusEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        userStatusService.setBotStateForUser(chatIdOne, MAIN_ASK);

        verify(userStatusMapper).toUserStatusEntityFromChatIdAndStatus(chatIdOne, MAIN_ASK);
        verify(userStatusRepository).save(expectedStatusASK);
    }

    @Test
    @DisplayName("Должен получить статус пользователя - MAIN_ASK, когда он есть в БД")
    public void getBotStateForUser_WhenStatusIsExists_ReturnsStatusOfASK() {
        when(userStatusRepository.findById(chatIdOne)).thenReturn(Optional.ofNullable(expectedStatusASK));

        BotState actualStatus = userStatusService.getBotStateForUser(chatIdOne);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actualStatus)
                .isNotNull();
        softAssertions.assertThat(actualStatus)
                .isEqualTo(expectedStatusASK.getStatus());

        verify(userStatusRepository).findById(chatIdOne);
        verify(userStatusRepository, never()).save(any(UserStatusEntity.class));
        verify(userStatusMapper, never()).toDefaultUserStatusEntityFromChatId(chatIdOne);
    }

    @Test
    @DisplayName("Должен получить дефолтный статус пользователя - MAIN_START, когда он не был найден в БД")
    public void getBotStateForUser_WhenStatusIsNotExists_ReturnsDefaultStatus() {
        when(userStatusRepository.findById(chatIdTwo)).thenReturn(Optional.empty());
        when(userStatusMapper.toDefaultUserStatusEntityFromChatId(chatIdTwo)).thenReturn(expectedStatusDefault);

        BotState actualStatus = userStatusService.getBotStateForUser(chatIdTwo);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actualStatus)
                .isNotNull();
        softAssertions.assertThat(actualStatus)
                .isEqualTo(expectedStatusDefault.getStatus());

        verify(userStatusRepository).findById(chatIdTwo);
        verify(userStatusRepository).save(expectedStatusDefault);
        verify(userStatusMapper).toDefaultUserStatusEntityFromChatId(chatIdTwo);
    }
}