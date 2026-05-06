package com.mindfocus.api.infrastructure.adapter.in.web.dto;

import com.mindfocus.api.domain.model.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Response DTO for Habit.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Habit response data")
public class HabitResponse {

    @Schema(description = "Unique identifier of the habit", example = "1")
    private Long id;

    @Schema(description = "Name of the habit", example = "Morning Reading")
    private String name;

    @Schema(description = "Description of the habit", example = "Read for 30 minutes every morning")
    private String description;

    @Schema(description = "Category of the habit", example = "STUDY")
    private Category category;

    @Schema(description = "Whether the habit is active", example = "true")
    private boolean active;

    @Schema(description = "Timestamp when the habit was created")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the habit was last updated")
    private LocalDateTime updatedAt;
}
