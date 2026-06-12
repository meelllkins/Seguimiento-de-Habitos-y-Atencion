package com.mindfocus.api.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * JPA entity for the habit_history table, maintaining a full audit trail.
 */
@Entity
@Table(name = "habit_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabitHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_history_habit"))
    private HabitEntity habit;

    @Column(name = "change_type", nullable = false, length = 20)
    private String changeType;

    @Column(length = 500)
    private String details;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;
}
