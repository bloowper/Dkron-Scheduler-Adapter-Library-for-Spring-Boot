package com.codibly.schedulerclient.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class JobIdTest {

    @Test
    void shouldThrowExceptionOnNullId() {
        // Given
        String id = null;

        // Then
        assertThrows(
                IllegalArgumentException.class,
                () -> new JobId(id)
        );
    }

    @Test
    void shouldThrowExceptionOnEmptyId() {
        // Given
        String id = "";

        // Then
        assertThrows(
                IllegalArgumentException.class,
                () -> new JobId(id)
        );
    }

    @Test
    void shouldCreateId() {
        // Given
        String id = "id";

        // When
        new JobId(id);
    }
}
