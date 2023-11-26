package com.github.jon7even.dto.gift;

import com.github.jon7even.entity.gift.GiftStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс DTO для добавления нового подарка
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftBuildingDto {
    private String name;
    private GiftStatus status;
}
