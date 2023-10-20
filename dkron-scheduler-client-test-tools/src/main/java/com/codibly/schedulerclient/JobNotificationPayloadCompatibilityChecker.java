package com.codibly.schedulerclient;


import org.junit.jupiter.api.Assertions;

import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

public class JobNotificationPayloadCompatibilityChecker {
    private final Object jobNotificationPayload;
    private BiConsumer<Object, Object> equalityChecker;

    private JobNotificationPayloadCompatibilityChecker(Object jobNotificationPayload) {
        this.jobNotificationPayload = jobNotificationPayload;
        this.equalityChecker = (a, b) -> assertThat(a).usingRecursiveComparison().isEqualTo(b);
    }

    public static JobNotificationPayloadCompatibilityChecker of(Object jobNotificationPayload) {
        return new JobNotificationPayloadCompatibilityChecker(jobNotificationPayload);
    }
    /**
     * override default equality checker function that is used for verifying payload equality before and after serialization.
     *
     * @param pojoEqualityChecker a consumer taking two payloads and asserting their equality
     * @return the compatibility checker instance
     */
    public JobNotificationPayloadCompatibilityChecker withPojoEqualityChecker(BiConsumer<Object, Object> pojoEqualityChecker) {
        this.equalityChecker = pojoEqualityChecker;
        return this;
    }

    /**
     * Verifies the serialization and deserialization compatibility of the job notification payload.
     * Throws an exception if the serialized and deserialized forms are not equal as per the set equality checker.
     */
    public void verifySerializationCompatibility() {
        Assertions.assertDoesNotThrow(() -> {
            String serialize = JobNotificationPayloadSerializer.serialize(jobNotificationPayload);
            Object deserialized = JobNotificationPayloadSerializer.deserialize(serialize);
            equalityChecker.accept(jobNotificationPayload, deserialized);
        },"provided  jobNotificationPayload cannot be serialized and deserialized in unchanged form");
    }


}
