package com.mindfocus.api.infrastructure.adapter.in.web;

import com.mindfocus.api.domain.model.Session;
import com.mindfocus.api.domain.port.in.SessionUseCase;
import com.mindfocus.api.infrastructure.adapter.in.web.dto.SessionRequest;
import com.mindfocus.api.infrastructure.adapter.in.web.dto.SessionResponse;
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
 * REST controller (input adapter) for productivity Session management.
 */
@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
@Tag(name = "Sessions", description = "Endpoints for managing productivity sessions and focus metrics")
public class SessionController {

    private final SessionUseCase sessionUseCase;

    @PostMapping
    @Operation(summary = "Create a new session", description = "Creates a new productivity session with focus metrics")
    public ResponseEntity<SessionResponse> createSession(@Valid @RequestBody SessionRequest request) {
        Session session = toDomain(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(sessionUseCase.createSession(session)));
    }

    @GetMapping
    @Operation(summary = "List all sessions", description = "Returns all sessions, optionally filtered by habit ID")
    public ResponseEntity<List<SessionResponse>> getAllSessions(
            @Parameter(description = "Filter by habit ID")
            @RequestParam(required = false) Long habitId) {
        List<Session> sessions = (habitId != null)
                ? sessionUseCase.getSessionsByHabitId(habitId)
                : sessionUseCase.getAllSessions();
        return ResponseEntity.ok(sessions.stream().map(this::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a session by ID", description = "Returns a specific session by its ID")
    public ResponseEntity<SessionResponse> getSessionById(
            @Parameter(description = "Session ID") @PathVariable Long id) {
        return ResponseEntity.ok(toResponse(sessionUseCase.getSessionById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a session", description = "Updates an existing session by its ID")
    public ResponseEntity<SessionResponse> updateSession(
            @Parameter(description = "Session ID") @PathVariable Long id,
            @Valid @RequestBody SessionRequest request) {
        Session session = toDomain(request);
        return ResponseEntity.ok(toResponse(sessionUseCase.updateSession(id, session)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a session", description = "Deletes a session by its ID")
    public ResponseEntity<Void> deleteSession(
            @Parameter(description = "Session ID") @PathVariable Long id) {
        sessionUseCase.deleteSession(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/habits/{habitId}/focus-score")
    @Operation(summary = "Get average focus score",
            description = "Returns the average focus score for all sessions of a given habit")
    public ResponseEntity<Double> getAverageFocusScore(
            @Parameter(description = "Habit ID") @PathVariable Long habitId) {
        return ResponseEntity.ok(sessionUseCase.getAverageFocusScoreByHabitId(habitId));
    }

    private Session toDomain(SessionRequest request) {
        return Session.builder()
                .habitId(request.getHabitId())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .durationMinutes(request.getDurationMinutes())
                .focusScore(request.getFocusScore())
                .distractions(request.getDistractions())
                .notes(request.getNotes())
                .build();
    }

    private SessionResponse toResponse(Session session) {
        return SessionResponse.builder()
                .id(session.getId())
                .habitId(session.getHabitId())
                .startTime(session.getStartTime())
                .endTime(session.getEndTime())
                .durationMinutes(session.getDurationMinutes())
                .focusScore(session.getFocusScore())
                .distractions(session.getDistractions())
                .notes(session.getNotes())
                .createdAt(session.getCreatedAt())
                .build();
    }
}
