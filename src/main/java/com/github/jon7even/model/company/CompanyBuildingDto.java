package com.github.jon7even.model.company;

import com.github.jon7even.telegram.menu.gift.TypeGift;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyBuildingDto {
    private String nameCompany;
    private Integer totalSum;
    private TypeGift type;
    private Boolean isGiven;
}
