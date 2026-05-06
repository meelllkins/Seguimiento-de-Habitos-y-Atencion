package com.mindfocus.api.infrastructure.adapter.out.persistence.repository;

import com.mindfocus.api.infrastructure.adapter.out.persistence.entity.HabitHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for HabitHistoryEntity.
 */
@Repository
public interface HabitHistoryJpaRepository extends JpaRepository<HabitHistoryEntity, Long> {

    List<HabitHistoryEntity> findByHabitId(Long habitId);
}
