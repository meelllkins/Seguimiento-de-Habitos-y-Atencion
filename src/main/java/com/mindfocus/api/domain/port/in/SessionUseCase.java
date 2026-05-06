package com.mindfocus.api.domain.port.in;

import com.mindfocus.api.domain.model.Session;

import java.util.List;

/**
 * Input port defining use cases for productivity Session management.
 */
public interface SessionUseCase {

    Session createSession(Session session);

    Session updateSession(Long id, Session session);

    void deleteSession(Long id);

    Session getSessionById(Long id);

    List<Session> getSessionsByHabitId(Long habitId);

    List<Session> getAllSessions();

    Double getAverageFocusScoreByHabitId(Long habitId);
}
