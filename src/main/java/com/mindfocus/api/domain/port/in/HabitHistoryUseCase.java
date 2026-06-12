package com.mindfocus.api.domain.port.in;

import com.mindfocus.api.domain.model.HabitHistory;

import java.util.List;

/**
 * Input port defining use cases for HabitHistory retrieval.
 */
public interface HabitHistoryUseCase {

    List<HabitHistory> getHistoryByHabitId(Long habitId);

    List<HabitHistory> getAllHistory();
}
