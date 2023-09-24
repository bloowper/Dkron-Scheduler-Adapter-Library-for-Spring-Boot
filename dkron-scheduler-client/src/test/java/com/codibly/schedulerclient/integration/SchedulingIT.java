package com.codibly.schedulerclient.integration;

import com.codibly.schedulerclient.DummyNotificationDto;
import com.codibly.schedulerclient.api.JobDescription;
import com.codibly.schedulerclient.api.JobScheduler;
import com.codibly.schedulerclient.api.Schedule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class SchedulingIT extends IntegrationTestBase {
// Uses default JonExecutionNotifier
    @Autowired
    private JobScheduler jobScheduler;
    @Autowired
    DummyNotificationJobExecutionListener dummyNotificationJobExecutionListener;

    @Test
    void createFixedJobAndAwaitForExecution() {
        // Given
        DummyNotificationDto notificationDto = new DummyNotificationDto("dummyValue");
        String id = UUID.randomUUID().toString();
        Instant now = Instant.now();
        JobDescription jobDescription = new JobDescription(
                id,
                notificationDto,
                Schedule.fixed(now.plus(Duration.ofSeconds(2)))
        );

        // When
        jobScheduler.scheduleJob(jobDescription);

        // Then
        await().atMost(Duration.ofSeconds(20)).until(() -> dummyNotificationJobExecutionListener.wasExecuted());
    }

    @Test
    void createIntervalJobAndAwaitForExecution() {
        fail("Not implemented yet");
    }

    @Test
    void createCronJobAndAwaitForExecution() {
        fail("Not implemented yet");
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        DummyNotificationJobExecutionListener dummyNotificationJobExecutionListener() {
            return new DummyNotificationJobExecutionListener();
        }
    }
}
