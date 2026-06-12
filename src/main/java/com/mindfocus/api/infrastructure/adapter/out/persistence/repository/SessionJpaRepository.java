package com.mindfocus.api.infrastructure.adapter.out.persistence.repository;

import com.mindfocus.api.infrastructure.adapter.out.persistence.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for SessionEntity.
 */
@Repository
public interface SessionJpaRepository extends JpaRepository<SessionEntity, Long> {

    List<SessionEntity> findByHabitId(Long habitId);

    @Query("SELECT AVG(s.focusScore) FROM SessionEntity s WHERE s.habit.id = :habitId")
    Double findAverageFocusScoreByHabitId(@Param("habitId") Long habitId);
}
