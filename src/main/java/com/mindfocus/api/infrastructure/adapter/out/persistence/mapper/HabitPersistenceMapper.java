package com.mindfocus.api.infrastructure.adapter.out.persistence.mapper;

import com.mindfocus.api.domain.model.Category;
import com.mindfocus.api.domain.model.Habit;
import com.mindfocus.api.infrastructure.adapter.out.persistence.entity.HabitEntity;
import org.springframework.stereotype.Component;

/**
 * Maps between Habit domain model and HabitEntity JPA entity.
 */
@Component
public class HabitPersistenceMapper {

    public Habit toDomain(HabitEntity entity) {
        return Habit.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .category(Category.valueOf(entity.getCategory().name()))
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public HabitEntity toEntity(Habit domain) {
        return HabitEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .category(HabitEntity.CategoryEnum.valueOf(domain.getCategory().name()))
                .active(domain.isActive())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
