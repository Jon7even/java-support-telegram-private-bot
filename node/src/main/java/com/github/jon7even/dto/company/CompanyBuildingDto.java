package com.github.jon7even.dto.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс DTO для создания новой компании
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyBuildingDto {
    private String name;
    private Integer totalSum;
    private Integer giftId;
    private Boolean isGiven;
}
