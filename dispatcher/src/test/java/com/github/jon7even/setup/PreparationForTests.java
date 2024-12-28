package com.github.jon7even.setup;

import com.github.jon7even.dto.UserAuthTrueDto;
import com.github.jon7even.dto.UserCreateDto;
import com.github.jon7even.entity.user.UserEntity;

import java.time.LocalDateTime;

/**
 * Подготовка данных для тестов
 *
 * @author Jon7even
 * @version 2.0
 */
public class PreparationForTests {

    protected UserEntity userEntityOne;
    protected UserEntity userEntityTwo;
    protected UserEntity userEntityThree;

    protected UserAuthTrueDto userAuthTrueDtoOne;
    protected UserAuthTrueDto UserAuthTrueDtoTwo;
    protected UserAuthTrueDto UserAuthTrueDtoThree;

    protected UserCreateDto userCreateDtoOne;

    protected Long userIdOne = 1L;
    protected Long userIdTwo = 2L;
    protected Long userIdThree = 3L;

    protected void initUserEntity() {

        userEntityOne = UserEntity.builder()
                .id(userIdOne)
                .chatId(1111111L)
                .firstName("FirstName")
                .lastName("FirstLastName")
                .userName("FirstUserName")
                .registeredOn(LocalDateTime.now())
                .build();

        userEntityTwo = UserEntity.builder()
                .id(userIdTwo)
                .chatId(2222222L)
                .registeredOn(LocalDateTime.now())
                .build();

        userEntityThree = UserEntity.builder()
                .id(userIdThree)
                .chatId(3333333L)
                .firstName("ThirdName")
                .lastName("ThirdLastName")
                .userName("ThirdUserName")
                .registeredOn(LocalDateTime.now())
                .authorization(true)
                .build();
    }

    protected void initUserDto() {

        userAuthTrueDtoOne = UserAuthTrueDto.builder()
                .chatId(1111111L)
                .firstName("FirstName")
                .lastName("FirstLastName")
                .userName("FirstUserName")
                .build();

        UserAuthTrueDtoTwo = UserAuthTrueDto.builder()
                .chatId(2222222L)
                .firstName("SecondName")
                .lastName("SecondLastName")
                .userName("SecondUserName")
                .build();

        UserAuthTrueDtoThree = UserAuthTrueDto.builder()
                .chatId(3333333L)
                .firstName("ThirdName")
                .lastName("ThirdLastName")
                .userName("ThirdUserName")
                .build();

        userCreateDtoOne = UserCreateDto.builder()
                .chatId(1111111L)
                .firstName("FirstName")
                .lastName("FirstLastName")
                .userName("FirstUserName")
                .build();
    }
}