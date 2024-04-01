package com.github.jon7even.dto.gift;

import com.github.jon7even.model.gift.GiftStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftShortDto {
    @NotNull
    private String name;

    @NotNull
    private GiftStatus status;
}
