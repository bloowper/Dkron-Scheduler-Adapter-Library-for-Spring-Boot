package com.github.bloowper.schedulerclient.api;


import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class IntervalScheduleTest {
    public static final Instant EARLY_INSTANT = Instant.parse("2021-01-01T00:00:00Z");
    public static final Instant LATER_INSTANT = Instant.parse("2021-01-01T00:00:00Z");
    @Test
    void shouldCreateIntervalSchedule() {
        // Given
        Duration interval = Duration.ofSeconds(60);

        // When
        Schedule.Interval schedule = Schedule.interval(interval);

        // Then
        assertEquals("@every 60s", schedule.scheduleExpression());
    }

    @Test
    void intervalScheduleCreationShouldFailedOnToShortDuration() {
        // Given
        Duration interval = Duration.ofMillis(250);

        // Then
        assertThatThrownBy(() -> Schedule.interval(interval))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Interval must be at least 1 second");
    }

    @Test
    void intervalScheduleCreationShouldFailedOnNegativeDuration() {
        // Given
        Duration interval = Duration.ofSeconds(-60);

        // Then
        assertThatThrownBy(() -> Schedule.interval(interval))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Interval must be positive");
    }

    @Test
    void shouldAddStartInstantToInterval(){
        // Given
        Schedule.Interval schedule = Schedule.interval(Duration.ofSeconds(60));

        // When
        Schedule.Interval scheduleWithStartInstant = schedule.withStartInstant(EARLY_INSTANT);

        // Then
        assertEquals(scheduleWithStartInstant.startOfTimeRange(),EARLY_INSTANT);
    }

    @Test
    void shouldAddEndInstantToInterval(){
        // Given
        Schedule.Interval schedule = Schedule.interval(Duration.ofSeconds(60));

        // When
        Schedule.Interval scheduleWithEndInstant = schedule.withEndInstant(EARLY_INSTANT);

        // Then
        assertEquals(scheduleWithEndInstant.endOfTimeRange(),EARLY_INSTANT);
    }
}
