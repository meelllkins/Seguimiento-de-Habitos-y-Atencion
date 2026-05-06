package com.mindfocus.api.application.service;

import com.mindfocus.api.domain.model.Category;
import com.mindfocus.api.domain.model.Habit;
import com.mindfocus.api.domain.model.HabitHistory;
import com.mindfocus.api.domain.port.in.HabitUseCase;
import com.mindfocus.api.domain.port.out.HabitHistoryRepositoryPort;
import com.mindfocus.api.domain.port.out.HabitRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Application service implementing the HabitUseCase input port.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class HabitService implements HabitUseCase {

    private final HabitRepositoryPort habitRepositoryPort;
    private final HabitHistoryRepositoryPort habitHistoryRepositoryPort;

    @Override
    public Habit createHabit(Habit habit) {
        habit.setCreatedAt(LocalDateTime.now());
        habit.setUpdatedAt(LocalDateTime.now());
        habit.setActive(true);
        Habit saved = habitRepositoryPort.save(habit);
        recordHistory(saved.getId(), "CREATED", "Habit created: " + saved.getName());
        return saved;
    }

    @Override
    public Habit updateHabit(Long id, Habit habit) {
        Habit existing = habitRepositoryPort.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Habit not found with id: " + id));
        existing.setName(habit.getName());
        existing.setDescription(habit.getDescription());
        existing.setCategory(habit.getCategory());
        existing.setActive(habit.isActive());
        existing.setUpdatedAt(LocalDateTime.now());
        Habit updated = habitRepositoryPort.save(existing);
        recordHistory(updated.getId(), "UPDATED", "Habit updated: " + updated.getName());
        return updated;
    }

    @Override
    public void deleteHabit(Long id) {
        if (!habitRepositoryPort.existsById(id)) {
            throw new NoSuchElementException("Habit not found with id: " + id);
        }
        recordHistory(id, "DELETED", "Habit deleted with id: " + id);
        habitRepositoryPort.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Habit getHabitById(Long id) {
        return habitRepositoryPort.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Habit not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Habit> getAllHabits() {
        return habitRepositoryPort.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Habit> getHabitsByCategory(String category) {
        Category.valueOf(category.toUpperCase());
        return habitRepositoryPort.findByCategory(category.toUpperCase());
    }

    private void recordHistory(Long habitId, String changeType, String details) {
        HabitHistory history = HabitHistory.builder()
                .habitId(habitId)
                .changeType(changeType)
                .details(details)
                .changedAt(LocalDateTime.now())
                .build();
        habitHistoryRepositoryPort.save(history);
    }
}
