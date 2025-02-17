package com.github.jon7even.dto.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс DTO для запроса к сервису, который предоставляет API нейросетей.
 *
 * @author Jon7even
 * @version 2.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiAskRequestDto {

    private String message;

    private String apiKey;
}