package com.github.jon7even.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Класс DTO для представления неавторизованного пользователя.
 *
 * @author Jon7even
 * @version 2.0
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthFalseDto {

    private Long id;

    private Long chatId;

    private String firstName;

    private String lastName;

    private String userName;

    private LocalDateTime registeredOn;

    private Integer attemptAuth;
}