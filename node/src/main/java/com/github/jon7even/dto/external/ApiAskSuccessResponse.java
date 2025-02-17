package com.github.jon7even.dto.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс DTO для получения успешного ответа от сервиса, который предоставляет API нейросетей.
 *
 * @author Jon7even
 * @version 2.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiAskSuccessResponse {

    private boolean isSuccess;

    private String response;

    private int usedWordsCount;

    private int usedTokensCount;
}