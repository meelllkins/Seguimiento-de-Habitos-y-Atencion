package com.mindfocus.api.application.service;

import com.mindfocus.api.domain.model.Session;
import com.mindfocus.api.domain.port.in.SessionUseCase;
import com.mindfocus.api.domain.port.out.HabitRepositoryPort;
import com.mindfocus.api.domain.port.out.SessionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Application service implementing the SessionUseCase input port.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SessionService implements SessionUseCase {

    private final SessionRepositoryPort sessionRepositoryPort;
    private final HabitRepositoryPort habitRepositoryPort;

    @Override
    public Session createSession(Session session) {
        if (!habitRepositoryPort.existsById(session.getHabitId())) {
            throw new NoSuchElementException("Habit not found with id: " + session.getHabitId());
        }
        session.setCreatedAt(LocalDateTime.now());
        return sessionRepositoryPort.save(session);
    }

    @Override
    public Session updateSession(Long id, Session session) {
        Session existing = sessionRepositoryPort.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Session not found with id: " + id));
        existing.setStartTime(session.getStartTime());
        existing.setEndTime(session.getEndTime());
        existing.setDurationMinutes(session.getDurationMinutes());
        existing.setFocusScore(session.getFocusScore());
        existing.setDistractions(session.getDistractions());
        existing.setNotes(session.getNotes());
        return sessionRepositoryPort.save(existing);
    }

    @Override
    public void deleteSession(Long id) {
        if (!sessionRepositoryPort.existsById(id)) {
            throw new NoSuchElementException("Session not found with id: " + id);
        }
        sessionRepositoryPort.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Session getSessionById(Long id) {
        return sessionRepositoryPort.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Session not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Session> getSessionsByHabitId(Long habitId) {
        if (!habitRepositoryPort.existsById(habitId)) {
            throw new NoSuchElementException("Habit not found with id: " + habitId);
        }
        return sessionRepositoryPort.findByHabitId(habitId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Session> getAllSessions() {
        return sessionRepositoryPort.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Double getAverageFocusScoreByHabitId(Long habitId) {
        if (!habitRepositoryPort.existsById(habitId)) {
            throw new NoSuchElementException("Habit not found with id: " + habitId);
        }
        return sessionRepositoryPort.findAverageFocusScoreByHabitId(habitId);
    }
}
