package com.mindfocus.api.infrastructure.adapter.out.persistence.mapper;

import com.mindfocus.api.domain.model.Session;
import com.mindfocus.api.infrastructure.adapter.out.persistence.entity.HabitEntity;
import com.mindfocus.api.infrastructure.adapter.out.persistence.entity.SessionEntity;
import org.springframework.stereotype.Component;

/**
 * Maps between Session domain model and SessionEntity JPA entity.
 */
@Component
public class SessionPersistenceMapper {

    public Session toDomain(SessionEntity entity) {
        return Session.builder()
                .id(entity.getId())
                .habitId(entity.getHabit().getId())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .durationMinutes(entity.getDurationMinutes())
                .focusScore(entity.getFocusScore())
                .distractions(entity.getDistractions())
                .notes(entity.getNotes())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public SessionEntity toEntity(Session domain) {
        HabitEntity habitRef = HabitEntity.builder().id(domain.getHabitId()).build();
        return SessionEntity.builder()
                .id(domain.getId())
                .habit(habitRef)
                .startTime(domain.getStartTime())
                .endTime(domain.getEndTime())
                .durationMinutes(domain.getDurationMinutes())
                .focusScore(domain.getFocusScore())
                .distractions(domain.getDistractions())
                .notes(domain.getNotes())
                .createdAt(domain.getCreatedAt())
                .build();
    }
}
