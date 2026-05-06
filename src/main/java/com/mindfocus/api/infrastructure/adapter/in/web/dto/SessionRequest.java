package com.mindfocus.api.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Request DTO for creating or updating a Session.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for creating or updating a productivity session")
public class SessionRequest {

    @NotNull(message = "Habit ID is required")
    @Schema(description = "ID of the associated habit", example = "1")
    private Long habitId;

    @NotNull(message = "Start time is required")
    @Schema(description = "Session start time", example = "2024-01-15T09:00:00")
    private LocalDateTime startTime;

    @Schema(description = "Session end time", example = "2024-01-15T09:30:00")
    private LocalDateTime endTime;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Schema(description = "Duration of the session in minutes", example = "30")
    private Integer durationMinutes;

    @Min(value = 0, message = "Focus score must be between 0 and 100")
    @Max(value = 100, message = "Focus score must be between 0 and 100")
    @Schema(description = "Focus score from 0 to 100", example = "85")
    private Integer focusScore;

    @Min(value = 0, message = "Distractions count cannot be negative")
    @Schema(description = "Number of distractions during the session", example = "3")
    private Integer distractions;

    @Schema(description = "Additional notes for the session", example = "Very productive session, completed chapter 3")
    private String notes;
}
