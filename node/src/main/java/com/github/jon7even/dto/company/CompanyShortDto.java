package com.github.jon7even.dto.company;

import com.github.jon7even.model.gift.GiftEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyShortDto {
    private String name;
    private Set<GiftEntity> gifts;
}
