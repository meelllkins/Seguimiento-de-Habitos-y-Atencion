package com.mindfocus.api.infrastructure.adapter.out.persistence.adapter;

import com.mindfocus.api.domain.model.HabitHistory;
import com.mindfocus.api.domain.port.out.HabitHistoryRepositoryPort;
import com.mindfocus.api.infrastructure.adapter.out.persistence.mapper.HabitHistoryPersistenceMapper;
import com.mindfocus.api.infrastructure.adapter.out.persistence.repository.HabitHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Output adapter implementing HabitHistoryRepositoryPort using Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class HabitHistoryPersistenceAdapter implements HabitHistoryRepositoryPort {

    private final HabitHistoryJpaRepository habitHistoryJpaRepository;
    private final HabitHistoryPersistenceMapper mapper;

    @Override
    public HabitHistory save(HabitHistory habitHistory) {
        return mapper.toDomain(habitHistoryJpaRepository.save(mapper.toEntity(habitHistory)));
    }

    @Override
    public List<HabitHistory> findByHabitId(Long habitId) {
        return habitHistoryJpaRepository.findByHabitId(habitId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<HabitHistory> findAll() {
        return habitHistoryJpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
