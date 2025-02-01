package com.github.jon7even.service.in.handle.factory;

import com.github.jon7even.exception.IllegalHandlerException;
import com.github.jon7even.service.in.handle.UserHandlerService;
import com.github.jon7even.service.in.handle.impl.AskHandlerImpl;
import com.github.jon7even.service.in.handle.impl.StandardCallbackHandlerImpl;
import com.github.jon7even.service.in.handle.impl.StandardTextHandlerImpl;
import com.github.jon7even.telegram.BotState;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Тестирование сервиса получения сообщений из RabbitMq {@link UserHandlerFactory}
 *
 * @author Jon7even
 * @version 2.0
 */
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@DisplayName("Тестирование методов сервиса UserHandlerFactory")
public class UserHandlerFactoryTest {

    private UserHandlerFactory userHandlerFactory;

    @Mock
    private StandardTextHandlerImpl standardTextHandler;

    @Mock
    private StandardCallbackHandlerImpl standardCallbackHandler;

    @Mock
    private AskHandlerImpl askHandler;

    @BeforeEach
    public void setUp() {
        userHandlerFactory = new UserHandlerFactory(List.of(standardTextHandler, standardCallbackHandler, askHandler));
    }

    @Test
    @DisplayName("Фабрика должна правильно проинициализироваться")
    public void initializeHandlers_Success() {
        HashMap<BotState, UserHandlerService> actualMap = (HashMap<BotState, UserHandlerService>)
                ReflectionTestUtils.getField(userHandlerFactory, "mapOfHandlersForUser");

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(userHandlerFactory).isNotNull();
            softly.assertThat(actualMap).isNotNull();
            softly.assertThat(actualMap).isNotEmpty();
            softly.assertThat(actualMap.values())
                    .contains(standardTextHandler, standardTextHandler, standardCallbackHandler);
            softly.assertAll();
        });
    }

    @Test
    @DisplayName("Ошибка при инициализации: мы не добавили новый обработчик в фабрику")
    public void initializeHandlers_ThrowsIllegalHandlerException() {
        UserHandlerService newHandler = new UserHandlerService() {
            @Override
            public void handle(Update update) {
            }
        };
        String expectedNameClass = newHandler.getClass().getName();

        assertThatThrownBy(() ->
                new UserHandlerFactory(List.of(standardTextHandler, standardCallbackHandler, askHandler, newHandler)))
                .isInstanceOf(IllegalHandlerException.class)
                .hasMessage(
                        String.format(
                                "В приложение был добавлен новый обработчик [%s], но не распределён в фабрике",
                                expectedNameClass
                        )
                );
    }

    @Test
    @DisplayName("Если передать NULL, фабрика должна вернуть стандартный обработчик")
    public void getHandlerForUser_WhenStatusIsNull_ReturnsDefaultHandler() {
        UserHandlerService actualResult = userHandlerFactory.getHandlerForUser(null);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualResult).isNotNull();
            softly.assertThat(actualResult).isEqualTo(standardTextHandler);
            softly.assertAll();
        });
    }

    @Test
    @DisplayName("Если передать статус MAIN_START должен вернуть StandardTextHandlerImpl")
    public void getHandlerForUser_WhenStatusIsStart_ReturnsStandardTextHandlerImpl() {
        UserHandlerService actualResult = userHandlerFactory.getHandlerForUser(BotState.MAIN_START);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualResult).isNotNull();
            softly.assertThat(actualResult).isEqualTo(standardTextHandler);
            softly.assertAll();
        });
    }

    @Test
    @DisplayName("Если передать статус MAIN_CALLBACK должен вернуть StandardCallbackHandlerImpl")
    public void getHandlerForUser_WhenStatusIsCallback_ReturnsStandardCallbackHandlerImpl() {
        UserHandlerService actualResult = userHandlerFactory.getHandlerForUser(BotState.MAIN_CALLBACK);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualResult).isNotNull();
            softly.assertThat(actualResult).isEqualTo(standardCallbackHandler);
            softly.assertAll();
        });
    }

    @Test
    @DisplayName("Если передать статус MAIN_ASK должен вернуть AskHandlerImpl")
    public void getHandlerForUser_WhenStatusIsASK_ReturnsAskHandlerImpl() {
        UserHandlerService actualResult = userHandlerFactory.getHandlerForUser(BotState.MAIN_ASK);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualResult).isNotNull();
            softly.assertThat(actualResult).isEqualTo(askHandler);
            softly.assertAll();
        });
    }
}