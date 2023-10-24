package com.codibly.schedulerclient.api;

import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.NotNull;


/**
 * Represents the description of a job to be executed. Encapsulates
 * the information needed to manage the job execution and its notifications.
 * When the job starts, a notification will be emitted through {@link com.codibly.schedulerclient.api.JobExecutionNotification}
 * using the specified fields.
 *
 * <ul>
 *   <li>{@code id} - The unique identifier of the job. This id will be used internally by dkron.
 *       Ensuring the uniqueness of this ID is the responsibility of the library client.</li>
 *   <li>{@code jobNotificationPayload} - Payload containing additional information that will be sent as part of
 *       the job execution notification. The payload can be used by listeners to
 *       perform specific actions upon receiving the notification. This payload must be a POJO with a no-args constructor, getters, and setters.</li>
 *   <li>{@code schedule} - Specifies the schedule for executing the job.
 *       Defines the frequency, time, and other relevant scheduling details.</li>
 * </ul>
 */
public record JobDescription(
        @NotNull String id,
        @NotNull Object jobNotificationPayload,
        @NotNull Schedule schedule
) {
    public JobDescription {
        if (ObjectUtils.anyNull(id,jobNotificationPayload,schedule)) {
            throw new IllegalArgumentException("All fields are required");
        }
    }
}

