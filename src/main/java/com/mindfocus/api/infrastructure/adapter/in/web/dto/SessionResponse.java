package com.mindfocus.api.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Response DTO for a productivity Session.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Productivity session response data")
public class SessionResponse {

    @Schema(description = "Unique identifier of the session", example = "1")
    private Long id;

    @Schema(description = "ID of the associated habit", example = "1")
    private Long habitId;

    @Schema(description = "Session start time", example = "2024-01-15T09:00:00")
    private LocalDateTime startTime;

    @Schema(description = "Session end time", example = "2024-01-15T09:30:00")
    private LocalDateTime endTime;

    @Schema(description = "Duration of the session in minutes", example = "30")
    private Integer durationMinutes;

    @Schema(description = "Focus score from 0 to 100", example = "85")
    private Integer focusScore;

    @Schema(description = "Number of distractions during the session", example = "3")
    private Integer distractions;

    @Schema(description = "Additional notes for the session", example = "Very productive session")
    private String notes;

    @Schema(description = "Timestamp when the session was created")
    private LocalDateTime createdAt;
}
