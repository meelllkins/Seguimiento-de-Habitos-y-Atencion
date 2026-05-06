package com.mindfocus.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Domain model representing a historical record of habit changes for full referential integrity.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabitHistory {

    private Long id;
    private Long habitId;
    private String changeType;
    private String details;
    private LocalDateTime changedAt;
}
