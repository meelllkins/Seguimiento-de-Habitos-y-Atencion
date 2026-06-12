package com.mindfocus.api.domain.port.out;

import com.mindfocus.api.domain.model.HabitHistory;

import java.util.List;

/**
 * Output port defining persistence operations for HabitHistory.
 */
public interface HabitHistoryRepositoryPort {

    HabitHistory save(HabitHistory habitHistory);

    List<HabitHistory> findByHabitId(Long habitId);

    List<HabitHistory> findAll();
}
