package com.github.jon7even.setup;

import com.github.jon7even.entity.user.UserEntity;

import java.time.LocalDateTime;

/**
 * Подготовка данных для тестов
 *
 * @author Jon7even
 * @version 2.0
 */
public class GenericTests {

    protected UserEntity userEntityOne;
    protected UserEntity userEntityTwo;
    protected UserEntity userEntityThree;

    protected Long idOne = 1L;
    protected Long idTwo = 2L;
    protected Long idThree = 3L;

    protected void initUserEntity() {

        userEntityOne = UserEntity.builder()
                .chatId(1111111L)
                .firstName("FirstName")
                .lastName("FirstLastName")
                .userName("FirstUserName")
                .authorization(true)
                .registeredOn(LocalDateTime.now())
                .build();

        userEntityTwo = UserEntity.builder()
                .chatId(2222222L)
                .firstName("SecondName")
                .userName("SecondUserName")
                .authorization(true)
                .registeredOn(LocalDateTime.now())
                .build();

        userEntityThree = UserEntity.builder()
                .chatId(3333333L)
                .firstName("ThirdName")
                .lastName("ThirdLastName")
                .authorization(true)
                .registeredOn(LocalDateTime.now())
                .build();
    }
}