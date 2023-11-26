package com.github.jon7even.dto.company;

import com.github.jon7even.entity.gift.GiftEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Класс DTO для краткого представления компании
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyShortDto {
    private String name;
    private Set<GiftEntity> gifts;
}
