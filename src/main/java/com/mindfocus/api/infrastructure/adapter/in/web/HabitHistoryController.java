package com.mindfocus.api.infrastructure.adapter.in.web;

import com.mindfocus.api.domain.model.HabitHistory;
import com.mindfocus.api.domain.port.in.HabitHistoryUseCase;
import com.mindfocus.api.infrastructure.adapter.in.web.dto.HabitHistoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller (input adapter) for HabitHistory audit trail.
 */
@RestController
@RequestMapping("/api/v1/habits")
@RequiredArgsConstructor
@Tag(name = "Habit History", description = "Endpoints for retrieving the habit change history")
public class HabitHistoryController {

    private final HabitHistoryUseCase habitHistoryUseCase;

    @GetMapping("/{habitId}/history")
    @Operation(summary = "Get history for a habit",
            description = "Returns the full audit trail of changes for a specific habit")
    public ResponseEntity<List<HabitHistoryResponse>> getHistoryByHabitId(
            @Parameter(description = "Habit ID") @PathVariable Long habitId) {
        List<HabitHistory> history = habitHistoryUseCase.getHistoryByHabitId(habitId);
        return ResponseEntity.ok(history.stream().map(this::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/history")
    @Operation(summary = "Get all habit history", description = "Returns the full audit trail for all habits")
    public ResponseEntity<List<HabitHistoryResponse>> getAllHistory() {
        List<HabitHistory> history = habitHistoryUseCase.getAllHistory();
        return ResponseEntity.ok(history.stream().map(this::toResponse).collect(Collectors.toList()));
    }

    private HabitHistoryResponse toResponse(HabitHistory history) {
        return HabitHistoryResponse.builder()
                .id(history.getId())
                .habitId(history.getHabitId())
                .changeType(history.getChangeType())
                .details(history.getDetails())
                .changedAt(history.getChangedAt())
                .build();
    }
}
