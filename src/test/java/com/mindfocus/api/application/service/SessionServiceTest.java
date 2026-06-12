package com.mindfocus.api.application.service;

import com.mindfocus.api.domain.model.Session;
import com.mindfocus.api.domain.port.out.HabitRepositoryPort;
import com.mindfocus.api.domain.port.out.SessionRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {

    @Mock
    private SessionRepositoryPort sessionRepositoryPort;

    @Mock
    private HabitRepositoryPort habitRepositoryPort;

    @InjectMocks
    private SessionService sessionService;

    private Session sampleSession;

    @BeforeEach
    void setUp() {
        sampleSession = Session.builder()
                .id(1L)
                .habitId(1L)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusMinutes(30))
                .durationMinutes(30)
                .focusScore(85)
                .distractions(2)
                .notes("Great focus today")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void createSession_whenHabitExists_shouldSaveSession() {
        Session input = Session.builder()
                .habitId(1L)
                .startTime(LocalDateTime.now())
                .durationMinutes(30)
                .focusScore(85)
                .build();

        when(habitRepositoryPort.existsById(1L)).thenReturn(true);
        when(sessionRepositoryPort.save(any(Session.class))).thenReturn(sampleSession);

        Session result = sessionService.createSession(input);

        assertThat(result).isNotNull();
        assertThat(result.getFocusScore()).isEqualTo(85);
        verify(sessionRepositoryPort).save(any(Session.class));
    }

    @Test
    void createSession_whenHabitNotFound_shouldThrowException() {
        Session input = Session.builder().habitId(99L).startTime(LocalDateTime.now()).build();
        when(habitRepositoryPort.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> sessionService.createSession(input))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Habit not found with id: 99");
    }

    @Test
    void getSessionById_whenExists_shouldReturnSession() {
        when(sessionRepositoryPort.findById(1L)).thenReturn(Optional.of(sampleSession));

        Session result = sessionService.getSessionById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getDurationMinutes()).isEqualTo(30);
    }

    @Test
    void getSessionById_whenNotFound_shouldThrowException() {
        when(sessionRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sessionService.getSessionById(99L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Session not found with id: 99");
    }

    @Test
    void getAllSessions_shouldReturnList() {
        when(sessionRepositoryPort.findAll()).thenReturn(List.of(sampleSession));

        List<Session> result = sessionService.getAllSessions();

        assertThat(result).hasSize(1);
    }

    @Test
    void getAverageFocusScore_whenHabitExists_shouldReturnAverage() {
        when(habitRepositoryPort.existsById(1L)).thenReturn(true);
        when(sessionRepositoryPort.findAverageFocusScoreByHabitId(1L)).thenReturn(78.5);

        Double avg = sessionService.getAverageFocusScoreByHabitId(1L);

        assertThat(avg).isEqualTo(78.5);
    }

    @Test
    void deleteSession_whenExists_shouldDelete() {
        when(sessionRepositoryPort.existsById(1L)).thenReturn(true);

        sessionService.deleteSession(1L);

        verify(sessionRepositoryPort).deleteById(1L);
    }

    @Test
    void deleteSession_whenNotFound_shouldThrowException() {
        when(sessionRepositoryPort.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> sessionService.deleteSession(99L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Session not found with id: 99");
    }
}
