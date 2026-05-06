package com.mindfocus.api.domain.port.out;

import com.mindfocus.api.domain.model.Session;

import java.util.List;
import java.util.Optional;

/**
 * Output port defining persistence operations for Session.
 */
public interface SessionRepositoryPort {

    Session save(Session session);

    Optional<Session> findById(Long id);

    List<Session> findAll();

    List<Session> findByHabitId(Long habitId);

    void deleteById(Long id);

    boolean existsById(Long id);

    Double findAverageFocusScoreByHabitId(Long habitId);
}
