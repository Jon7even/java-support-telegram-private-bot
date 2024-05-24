package com.github.jon7even.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс DTO для регистрации нового пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {
    private Long chatId;
    private String firstName;
    private String lastName;
    private String userName;
}
