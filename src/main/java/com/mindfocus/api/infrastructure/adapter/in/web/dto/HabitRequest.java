package com.mindfocus.api.infrastructure.adapter.in.web.dto;

import com.mindfocus.api.domain.model.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request DTO for creating or updating a Habit.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for creating or updating a habit")
public class HabitRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 150, message = "Name must not exceed 150 characters")
    @Schema(description = "Name of the habit", example = "Morning Reading")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Schema(description = "Description of the habit", example = "Read for 30 minutes every morning")
    private String description;

    @NotNull(message = "Category is required")
    @Schema(description = "Category of the habit", example = "STUDY",
            allowableValues = {"WORK", "STUDY", "LEISURE", "SOCIAL_MEDIA"})
    private Category category;

    @Schema(description = "Whether the habit is active", example = "true", defaultValue = "true")
    private boolean active = true;
}
