package com.github.jon7even.dto.gift;

import com.github.jon7even.entity.gift.GiftStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftShortDto {
    private String name;
    private GiftStatus status;
}
