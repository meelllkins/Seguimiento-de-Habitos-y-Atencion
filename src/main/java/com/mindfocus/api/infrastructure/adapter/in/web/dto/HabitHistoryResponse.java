package com.mindfocus.api.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Response DTO for HabitHistory.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Habit history audit record response data")
public class HabitHistoryResponse {

    @Schema(description = "Unique identifier of the history record", example = "1")
    private Long id;

    @Schema(description = "ID of the associated habit", example = "1")
    private Long habitId;

    @Schema(description = "Type of change (CREATED, UPDATED, DELETED)", example = "CREATED")
    private String changeType;

    @Schema(description = "Details of the change", example = "Habit created: Morning Reading")
    private String details;

    @Schema(description = "Timestamp when the change occurred")
    private LocalDateTime changedAt;
}
