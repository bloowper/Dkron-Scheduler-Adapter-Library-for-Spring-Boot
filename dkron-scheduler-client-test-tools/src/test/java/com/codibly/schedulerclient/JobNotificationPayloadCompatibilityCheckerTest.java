package com.codibly.schedulerclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.*;

class JobNotificationPayloadCompatibilityCheckerTest {

    @Test
    void shouldSuccessOnPojo(){
        // Given
        Pojo pojo = new Pojo();
        pojo.setField("value");

        // Then
        JobNotificationPayloadCompatibilityChecker.of(pojo).verifySerializationCompatibility();
    }

    @Test
    void shouldFailOnNotPojo(){
        // Given
        NotPojo notPojo = new NotPojo("value");

        // Then
        assertThrows(AssertionError.class, () -> JobNotificationPayloadCompatibilityChecker.of(notPojo).verifySerializationCompatibility());
    }

    @Test
    void shouldFailWithCustomEqualityCheck() {
        // Given
        Pojo pojo = new Pojo();
        pojo.setField("value");
        BiConsumer<Object, Object> equalityCheck = (a, b) -> assertEquals(a, b);

        // Then
        assertThrows(AssertionError.class, () -> JobNotificationPayloadCompatibilityChecker.of(pojo).withPojoEqualityChecker(equalityCheck).verifySerializationCompatibility());
    }


    @NoArgsConstructor
    @Getter
    @Setter
    private static class Pojo{
        private String field;
    }

    @AllArgsConstructor
    @Getter
    private static class NotPojo{
        private String field;
    }
}

