package com.mindfocus.api.domain.port.in;

import com.mindfocus.api.domain.model.Habit;

import java.util.List;

/**
 * Input port defining use cases for Habit management.
 */
public interface HabitUseCase {

    Habit createHabit(Habit habit);

    Habit updateHabit(Long id, Habit habit);

    void deleteHabit(Long id);

    Habit getHabitById(Long id);

    List<Habit> getAllHabits();

    List<Habit> getHabitsByCategory(String category);
}
