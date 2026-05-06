package com.mindfocus.api.application.service;

import com.mindfocus.api.domain.model.HabitHistory;
import com.mindfocus.api.domain.port.in.HabitHistoryUseCase;
import com.mindfocus.api.domain.port.out.HabitHistoryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Application service implementing the HabitHistoryUseCase input port.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HabitHistoryService implements HabitHistoryUseCase {

    private final HabitHistoryRepositoryPort habitHistoryRepositoryPort;

    @Override
    public List<HabitHistory> getHistoryByHabitId(Long habitId) {
        return habitHistoryRepositoryPort.findByHabitId(habitId);
    }

    @Override
    public List<HabitHistory> getAllHistory() {
        return habitHistoryRepositoryPort.findAll();
    }
}
