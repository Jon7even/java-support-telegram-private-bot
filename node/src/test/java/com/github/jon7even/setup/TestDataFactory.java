package com.github.jon7even.setup;

import com.github.jon7even.entity.user.UserEntity;
import com.github.jon7even.utils.MessageUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.time.LocalDateTime;

/**
 * Подготовка данных для тестов
 *
 * @author Jon7even
 * @version 2.0
 */
public class TestDataFactory {

    protected UserEntity userEntityOne;
    protected UserEntity userEntityTwo;
    protected UserEntity userEntityThree;

    protected Long chatIdOne = 1111111L;
    protected Long chatIdTwo = 2222222L;
    protected Long chatIdThree = 3333333L;

    protected Long idOne = 1L;
    protected Long idTwo = 2L;
    protected Long idThree = 3L;

    protected Message expectedMessage;
    protected SendMessage expectedSendMessage;
    protected EditMessageText expectedEditMessageText;

    protected void initMessage() {
        initUserEntity();

        expectedMessage = Message.builder()
                .chat(Chat.builder()
                        .id(chatIdOne)
                        .type("private")
                        .firstName(userEntityOne.getFirstName())
                        .lastName(userEntityOne.getLastName())
                        .userName(userEntityOne.getLastName())
                        .build())
                .text("test message")
                .build();

        expectedSendMessage = MessageUtils.buildAnswerWithText(expectedMessage.getChatId(), "test message");

        expectedEditMessageText = MessageUtils.buildAnswerWithEditText(
                expectedMessage.getChatId(), "edit message", 1
        );
    }

    protected void initUserEntity() {

        userEntityOne = UserEntity.builder()
                .chatId(chatIdOne)
                .firstName("FirstName")
                .lastName("FirstLastName")
                .userName("FirstUserName")
                .authorization(true)
                .registeredOn(LocalDateTime.now())
                .build();

        userEntityTwo = UserEntity.builder()
                .chatId(chatIdTwo)
                .firstName("SecondName")
                .userName("SecondUserName")
                .authorization(true)
                .registeredOn(LocalDateTime.now())
                .build();

        userEntityThree = UserEntity.builder()
                .chatId(chatIdThree)
                .firstName("ThirdName")
                .lastName("ThirdLastName")
                .authorization(true)
                .registeredOn(LocalDateTime.now())
                .build();
    }
}