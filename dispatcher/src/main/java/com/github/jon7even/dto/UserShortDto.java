package com.github.jon7even.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserShortDto {
    @NotNull
    private Long chatId;

    private String firstName;

    private String lastName;

    private String userName;

    private String textMessage;
}
