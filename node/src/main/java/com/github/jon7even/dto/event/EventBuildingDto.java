package com.github.jon7even.dto.event;

import com.github.jon7even.entity.event.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventBuildingDto {
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private EventStatus status;
}
