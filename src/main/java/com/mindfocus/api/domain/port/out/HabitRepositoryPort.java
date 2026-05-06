package com.mindfocus.api.domain.port.out;

import com.mindfocus.api.domain.model.Habit;

import java.util.List;
import java.util.Optional;

/**
 * Output port defining persistence operations for Habit.
 */
public interface HabitRepositoryPort {

    Habit save(Habit habit);

    Optional<Habit> findById(Long id);

    List<Habit> findAll();

    List<Habit> findByCategory(String category);

    void deleteById(Long id);

    boolean existsById(Long id);
}
