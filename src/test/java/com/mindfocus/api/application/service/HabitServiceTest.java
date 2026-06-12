package com.mindfocus.api.application.service;

import com.mindfocus.api.domain.model.Category;
import com.mindfocus.api.domain.model.Habit;
import com.mindfocus.api.domain.model.HabitHistory;
import com.mindfocus.api.domain.port.out.HabitHistoryRepositoryPort;
import com.mindfocus.api.domain.port.out.HabitRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HabitServiceTest {

    @Mock
    private HabitRepositoryPort habitRepositoryPort;

    @Mock
    private HabitHistoryRepositoryPort habitHistoryRepositoryPort;

    @InjectMocks
    private HabitService habitService;

    private Habit sampleHabit;

    @BeforeEach
    void setUp() {
        sampleHabit = Habit.builder()
                .id(1L)
                .name("Morning Reading")
                .description("Read every morning")
                .category(Category.STUDY)
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void createHabit_shouldSaveAndRecordHistory() {
        Habit input = Habit.builder()
                .name("Morning Reading")
                .description("Read every morning")
                .category(Category.STUDY)
                .build();

        when(habitRepositoryPort.save(any(Habit.class))).thenReturn(sampleHabit);
        when(habitHistoryRepositoryPort.save(any(HabitHistory.class))).thenReturn(HabitHistory.builder().build());

        Habit result = habitService.createHabit(input);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Morning Reading");
        verify(habitRepositoryPort).save(any(Habit.class));
        verify(habitHistoryRepositoryPort).save(any(HabitHistory.class));
    }

    @Test
    void getHabitById_whenExists_shouldReturnHabit() {
        when(habitRepositoryPort.findById(1L)).thenReturn(Optional.of(sampleHabit));

        Habit result = habitService.getHabitById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Morning Reading");
    }

    @Test
    void getHabitById_whenNotFound_shouldThrowException() {
        when(habitRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> habitService.getHabitById(99L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Habit not found with id: 99");
    }

    @Test
    void getAllHabits_shouldReturnList() {
        when(habitRepositoryPort.findAll()).thenReturn(List.of(sampleHabit));

        List<Habit> result = habitService.getAllHabits();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory()).isEqualTo(Category.STUDY);
    }

    @Test
    void updateHabit_whenExists_shouldUpdateAndRecordHistory() {
        Habit updated = Habit.builder()
                .name("Evening Reading")
                .description("Read in the evening")
                .category(Category.LEISURE)
                .active(true)
                .build();

        when(habitRepositoryPort.findById(1L)).thenReturn(Optional.of(sampleHabit));
        when(habitRepositoryPort.save(any(Habit.class))).thenAnswer(i -> i.getArgument(0));
        when(habitHistoryRepositoryPort.save(any(HabitHistory.class))).thenReturn(HabitHistory.builder().build());

        Habit result = habitService.updateHabit(1L, updated);

        assertThat(result.getName()).isEqualTo("Evening Reading");
        assertThat(result.getCategory()).isEqualTo(Category.LEISURE);
        verify(habitHistoryRepositoryPort).save(any(HabitHistory.class));
    }

    @Test
    void deleteHabit_whenExists_shouldDeleteAndRecordHistory() {
        when(habitRepositoryPort.existsById(1L)).thenReturn(true);
        when(habitHistoryRepositoryPort.save(any(HabitHistory.class))).thenReturn(HabitHistory.builder().build());

        habitService.deleteHabit(1L);

        verify(habitRepositoryPort).deleteById(1L);
        verify(habitHistoryRepositoryPort).save(any(HabitHistory.class));
    }

    @Test
    void deleteHabit_whenNotFound_shouldThrowException() {
        when(habitRepositoryPort.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> habitService.deleteHabit(99L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Habit not found with id: 99");
    }

    @Test
    void getHabitsByCategory_withValidCategory_shouldReturnFiltered() {
        when(habitRepositoryPort.findByCategory("STUDY")).thenReturn(List.of(sampleHabit));

        List<Habit> result = habitService.getHabitsByCategory("STUDY");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory()).isEqualTo(Category.STUDY);
    }

    @Test
    void getHabitsByCategory_withInvalidCategory_shouldThrowException() {
        assertThatThrownBy(() -> habitService.getHabitsByCategory("INVALID"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
