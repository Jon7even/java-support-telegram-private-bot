package com.github.jon7even.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Класс DTO для представления авторизованного пользователя.
 *
 * @author Jon7even
 * @version 2.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthTrueDto {

    private Long id;

    private Long chatId;

    private String firstName;

    private String lastName;

    private String userName;

    private LocalDateTime registeredOn;
}