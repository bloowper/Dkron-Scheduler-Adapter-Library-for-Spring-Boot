package com.github.bloowper.schedulerclient.integration;

import com.github.bloowper.schedulerclient.DummyNotificationDto;
import com.github.bloowper.schedulerclient.api.JobDescription;
import com.github.bloowper.schedulerclient.api.JobId;
import com.github.bloowper.schedulerclient.api.JobScheduler;
import com.github.bloowper.schedulerclient.api.Schedule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Duration;
import java.time.Instant;

import static org.awaitility.Awaitility.await;


class SchedulingExecutionIT extends IntegrationTestBase {
// Uses default JonExecutionNotifier
    @Autowired
    private JobScheduler jobScheduler;
    @Autowired
    JobExecutionListener jobExecutionListener;

    // TODO: better handling of job execution listener then @DirtyContext
    @Test
    @DirtiesContext
    void createFixedJobAndAwaitForExecution() {
        // Given
        DummyNotificationDto notificationDto = new DummyNotificationDto("dummyValue1");
        System.out.println("notificationDto: " + notificationDto);
        JobId id = JobId.autoGenerated();
        Instant now = Instant.now();
        JobDescription jobDescription = new JobDescription(
                id,
                notificationDto,
                Schedule.fixed(now.plus(Duration.ofSeconds(3)))
        );

        // When
        jobScheduler.scheduleJob(jobDescription);

        // Then
        await().atMost(Duration.ofSeconds(30)).until(() -> jobExecutionListener.wasExecuted(notificationDto,1));
    }

    @Test
    @DirtiesContext
    void createIntervalJobAndAwaitForExecution() {
        // Given
        DummyNotificationDto notificationDto = new DummyNotificationDto("dummyValue2");
        System.out.println("notificationDto: " + notificationDto);

        JobId id = JobId.autoGenerated();
        JobDescription jobDescription = new JobDescription(
                id,
                notificationDto,
                Schedule.interval(Duration.ofSeconds(3))
        );

        // When
        jobScheduler.scheduleJob(jobDescription);

        // Then
        await().atMost(Duration.ofSeconds(30)).until(() -> jobExecutionListener.wasExecuted(notificationDto,2));
    }

    @Test
    @DirtiesContext
    void createCronJobAndAwaitForExecution() {
        // Given
        DummyNotificationDto notificationDto = new DummyNotificationDto("dummyValue3");
        System.out.println("notificationDto: " + notificationDto);

        JobId id = JobId.autoGenerated();
        JobDescription jobDescription = new JobDescription(
                id,
                notificationDto,
                Schedule.cron("*/4 * * * * *")
        );

        // When
        jobScheduler.scheduleJob(jobDescription);

        // Then
        await().atMost(Duration.ofSeconds(30)).until(() -> jobExecutionListener.wasExecuted(notificationDto,1));
    }

}
