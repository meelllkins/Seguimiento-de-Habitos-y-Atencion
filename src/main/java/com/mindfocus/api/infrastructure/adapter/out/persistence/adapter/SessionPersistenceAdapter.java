package com.mindfocus.api.infrastructure.adapter.out.persistence.adapter;

import com.mindfocus.api.domain.model.Session;
import com.mindfocus.api.domain.port.out.SessionRepositoryPort;
import com.mindfocus.api.infrastructure.adapter.out.persistence.mapper.SessionPersistenceMapper;
import com.mindfocus.api.infrastructure.adapter.out.persistence.repository.SessionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Output adapter implementing SessionRepositoryPort using Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class SessionPersistenceAdapter implements SessionRepositoryPort {

    private final SessionJpaRepository sessionJpaRepository;
    private final SessionPersistenceMapper mapper;

    @Override
    public Session save(Session session) {
        return mapper.toDomain(sessionJpaRepository.save(mapper.toEntity(session)));
    }

    @Override
    public Optional<Session> findById(Long id) {
        return sessionJpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Session> findAll() {
        return sessionJpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Session> findByHabitId(Long habitId) {
        return sessionJpaRepository.findByHabitId(habitId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        sessionJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return sessionJpaRepository.existsById(id);
    }

    @Override
    public Double findAverageFocusScoreByHabitId(Long habitId) {
        return sessionJpaRepository.findAverageFocusScoreByHabitId(habitId);
    }
}
