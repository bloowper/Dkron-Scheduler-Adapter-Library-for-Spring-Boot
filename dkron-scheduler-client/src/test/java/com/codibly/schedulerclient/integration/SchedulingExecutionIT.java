package com.codibly.schedulerclient.integration;

import com.codibly.schedulerclient.DummyNotificationDto;
import com.codibly.schedulerclient.api.JobDescription;
import com.codibly.schedulerclient.api.JobScheduler;
import com.codibly.schedulerclient.api.Schedule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.fail;

class SchedulingExecutionIT extends IntegrationTestBase {
// Uses default JonExecutionNotifier
    @Autowired
    private JobScheduler jobScheduler;
    @Autowired
    JobExecutionListener jobExecutionListener;

    @Test
    void createFixedJobAndAwaitForExecution() {
        // Given
        DummyNotificationDto notificationDto = new DummyNotificationDto("dummyValue1");
        String id = UUID.randomUUID().toString();
        Instant now = Instant.now();
        JobDescription jobDescription = new JobDescription(
                id,
                notificationDto,
                Schedule.fixed(now.plus(Duration.ofSeconds(3)))
        );

        // When
        jobScheduler.scheduleJob(jobDescription);

        // Then
        await().atMost(Duration.ofSeconds(6)).until(() -> jobExecutionListener.wasExecuted(notificationDto,1));
    }

    @Test
    void createIntervalJobAndAwaitForExecution() {
        // Given
        DummyNotificationDto notificationDto = new DummyNotificationDto("dummyValue2");
        String id = UUID.randomUUID().toString();
        JobDescription jobDescription = new JobDescription(
                id,
                notificationDto,
                Schedule.interval(Duration.ofSeconds(3))
        );

        // When
        jobScheduler.scheduleJob(jobDescription);

        // Then
        await().atMost(Duration.ofSeconds(7)).until(() -> jobExecutionListener.wasExecuted(notificationDto,2));
    }

    @Test
    void createCronJobAndAwaitForExecution() {
        // Given
        DummyNotificationDto notificationDto = new DummyNotificationDto("dummyValue3");
        String id = UUID.randomUUID().toString();
        JobDescription jobDescription = new JobDescription(
                id,
                notificationDto,
                Schedule.cron("*/4 * * * * *")
        );

        // When
        jobScheduler.scheduleJob(jobDescription);

        // Then
        await().atMost(Duration.ofSeconds(7)).until(() -> jobExecutionListener.wasExecuted(notificationDto,1));
    }

}
