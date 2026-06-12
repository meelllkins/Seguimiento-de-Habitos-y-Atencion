package com.mindfocus.api.infrastructure.adapter.out.persistence.repository;

import com.mindfocus.api.infrastructure.adapter.out.persistence.entity.HabitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for HabitEntity.
 */
@Repository
public interface HabitJpaRepository extends JpaRepository<HabitEntity, Long> {

    List<HabitEntity> findByCategory(HabitEntity.CategoryEnum category);
}
