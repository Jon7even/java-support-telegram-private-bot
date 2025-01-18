package com.github.jon7even.setup;

import com.github.jon7even.dto.UserAuthFalseDto;
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
public class TestDataFactory {

    protected Long userIdOne = 1L;

    protected UserEntity userEntityOne;
    protected UserEntity userEntityTwo;
    protected UserEntity userEntityThree;

    protected UserCreateDto userCreateDtoOne;
    protected UserAuthTrueDto userAuthTrueDtoOne;
    protected UserAuthFalseDto userAuthFalseDtoOne;

    protected void initUserEntity() {

        userEntityOne = UserEntity.builder()
                .chatId(1111111L)
                .firstName("FirstName")
                .lastName("FirstLastName")
                .userName("FirstUserName")
                .registeredOn(LocalDateTime.now())
                .build();

        userEntityTwo = UserEntity.builder()
                .chatId(2222222L)
                .registeredOn(LocalDateTime.now())
                .build();

        userEntityThree = UserEntity.builder()
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

        userCreateDtoOne = UserCreateDto.builder()
                .chatId(1111111L)
                .firstName("FirstName")
                .lastName("FirstLastName")
                .userName("FirstUserName")
                .build();

        userAuthFalseDtoOne = UserAuthFalseDto.builder()
                .id(userIdOne)
                .chatId(1111111L)
                .firstName("FirstName")
                .lastName("FirstLastName")
                .userName("FirstUserName")
                .registeredOn(LocalDateTime.now())
                .attemptAuth(0)
                .build();
    }
}