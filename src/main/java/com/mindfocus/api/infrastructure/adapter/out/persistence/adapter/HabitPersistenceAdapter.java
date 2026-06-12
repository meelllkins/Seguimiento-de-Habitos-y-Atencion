package com.mindfocus.api.infrastructure.adapter.out.persistence.adapter;

import com.mindfocus.api.domain.model.Habit;
import com.mindfocus.api.domain.port.out.HabitRepositoryPort;
import com.mindfocus.api.infrastructure.adapter.out.persistence.entity.HabitEntity;
import com.mindfocus.api.infrastructure.adapter.out.persistence.mapper.HabitPersistenceMapper;
import com.mindfocus.api.infrastructure.adapter.out.persistence.repository.HabitJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Output adapter implementing HabitRepositoryPort using Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class HabitPersistenceAdapter implements HabitRepositoryPort {

    private final HabitJpaRepository habitJpaRepository;
    private final HabitPersistenceMapper mapper;

    @Override
    public Habit save(Habit habit) {
        HabitEntity entity = mapper.toEntity(habit);
        return mapper.toDomain(habitJpaRepository.save(entity));
    }

    @Override
    public Optional<Habit> findById(Long id) {
        return habitJpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Habit> findAll() {
        return habitJpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Habit> findByCategory(String category) {
        HabitEntity.CategoryEnum categoryEnum = HabitEntity.CategoryEnum.valueOf(category);
        return habitJpaRepository.findByCategory(categoryEnum).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        habitJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return habitJpaRepository.existsById(id);
    }
}
