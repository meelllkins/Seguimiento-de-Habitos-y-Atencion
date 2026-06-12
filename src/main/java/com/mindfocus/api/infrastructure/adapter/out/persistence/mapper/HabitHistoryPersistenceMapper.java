package com.mindfocus.api.infrastructure.adapter.out.persistence.mapper;

import com.mindfocus.api.domain.model.HabitHistory;
import com.mindfocus.api.infrastructure.adapter.out.persistence.entity.HabitEntity;
import com.mindfocus.api.infrastructure.adapter.out.persistence.entity.HabitHistoryEntity;
import org.springframework.stereotype.Component;

/**
 * Maps between HabitHistory domain model and HabitHistoryEntity JPA entity.
 */
@Component
public class HabitHistoryPersistenceMapper {

    public HabitHistory toDomain(HabitHistoryEntity entity) {
        return HabitHistory.builder()
                .id(entity.getId())
                .habitId(entity.getHabit().getId())
                .changeType(entity.getChangeType())
                .details(entity.getDetails())
                .changedAt(entity.getChangedAt())
                .build();
    }

    public HabitHistoryEntity toEntity(HabitHistory domain) {
        HabitEntity habitRef = HabitEntity.builder().id(domain.getHabitId()).build();
        return HabitHistoryEntity.builder()
                .id(domain.getId())
                .habit(habitRef)
                .changeType(domain.getChangeType())
                .details(domain.getDetails())
                .changedAt(domain.getChangedAt())
                .build();
    }
}
