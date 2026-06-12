package com.mindfocus.api.infrastructure.adapter.in.web;

import com.mindfocus.api.domain.model.Habit;
import com.mindfocus.api.domain.port.in.HabitUseCase;
import com.mindfocus.api.infrastructure.adapter.in.web.dto.HabitRequest;
import com.mindfocus.api.infrastructure.adapter.in.web.dto.HabitResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller (input adapter) for Habit management.
 */
@RestController
@RequestMapping("/api/v1/habits")
@RequiredArgsConstructor
@Tag(name = "Habits", description = "Endpoints for managing habits and their categorization")
public class HabitController {

    private final HabitUseCase habitUseCase;

    @PostMapping
    @Operation(summary = "Create a new habit", description = "Creates a new habit with the given details")
    public ResponseEntity<HabitResponse> createHabit(@Valid @RequestBody HabitRequest request) {
        Habit habit = toDomain(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(habitUseCase.createHabit(habit)));
    }

    @GetMapping
    @Operation(summary = "List all habits", description = "Returns all habits, optionally filtered by category")
    public ResponseEntity<List<HabitResponse>> getAllHabits(
            @Parameter(description = "Filter by category: WORK, STUDY, LEISURE, SOCIAL_MEDIA")
            @RequestParam(required = false) String category) {
        List<Habit> habits = (category != null && !category.isBlank())
                ? habitUseCase.getHabitsByCategory(category)
                : habitUseCase.getAllHabits();
        return ResponseEntity.ok(habits.stream().map(this::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a habit by ID", description = "Returns a specific habit by its ID")
    public ResponseEntity<HabitResponse> getHabitById(
            @Parameter(description = "Habit ID") @PathVariable Long id) {
        return ResponseEntity.ok(toResponse(habitUseCase.getHabitById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a habit", description = "Updates an existing habit by its ID")
    public ResponseEntity<HabitResponse> updateHabit(
            @Parameter(description = "Habit ID") @PathVariable Long id,
            @Valid @RequestBody HabitRequest request) {
        Habit habit = toDomain(request);
        return ResponseEntity.ok(toResponse(habitUseCase.updateHabit(id, habit)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a habit", description = "Deletes a habit by its ID")
    public ResponseEntity<Void> deleteHabit(
            @Parameter(description = "Habit ID") @PathVariable Long id) {
        habitUseCase.deleteHabit(id);
        return ResponseEntity.noContent().build();
    }

    private Habit toDomain(HabitRequest request) {
        return Habit.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .active(request.isActive())
                .build();
    }

    private HabitResponse toResponse(Habit habit) {
        return HabitResponse.builder()
                .id(habit.getId())
                .name(habit.getName())
                .description(habit.getDescription())
                .category(habit.getCategory())
                .active(habit.isActive())
                .createdAt(habit.getCreatedAt())
                .updatedAt(habit.getUpdatedAt())
                .build();
    }
}
