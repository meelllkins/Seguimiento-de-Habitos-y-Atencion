-- V1: Create core tables for MindFocus API

CREATE TABLE IF NOT EXISTS habits (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(150) NOT NULL,
    description VARCHAR(500),
    category    VARCHAR(30)  NOT NULL,
    active      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  DATETIME     NOT NULL,
    updated_at  DATETIME     NOT NULL,
    CONSTRAINT pk_habits PRIMARY KEY (id),
    CONSTRAINT chk_habits_category CHECK (category IN ('WORK', 'STUDY', 'LEISURE', 'SOCIAL_MEDIA'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS sessions (
    id               BIGINT   NOT NULL AUTO_INCREMENT,
    habit_id         BIGINT   NOT NULL,
    start_time       DATETIME NOT NULL,
    end_time         DATETIME,
    duration_minutes INT,
    focus_score      INT,
    distractions     INT,
    notes            VARCHAR(1000),
    created_at       DATETIME NOT NULL,
    CONSTRAINT pk_sessions PRIMARY KEY (id),
    CONSTRAINT fk_session_habit FOREIGN KEY (habit_id) REFERENCES habits (id) ON DELETE CASCADE,
    CONSTRAINT chk_focus_score CHECK (focus_score IS NULL OR (focus_score >= 0 AND focus_score <= 100)),
    CONSTRAINT chk_distractions CHECK (distractions IS NULL OR distractions >= 0),
    CONSTRAINT chk_duration CHECK (duration_minutes IS NULL OR duration_minutes >= 1)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS habit_history (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    habit_id    BIGINT       NOT NULL,
    change_type VARCHAR(20)  NOT NULL,
    details     VARCHAR(500),
    changed_at  DATETIME     NOT NULL,
    CONSTRAINT pk_habit_history PRIMARY KEY (id),
    CONSTRAINT fk_history_habit FOREIGN KEY (habit_id) REFERENCES habits (id) ON DELETE CASCADE,
    CONSTRAINT chk_change_type CHECK (change_type IN ('CREATED', 'UPDATED', 'DELETED'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Indexes for performance
CREATE INDEX idx_habits_category ON habits (category);
CREATE INDEX idx_sessions_habit_id ON sessions (habit_id);
CREATE INDEX idx_history_habit_id ON habit_history (habit_id);
