package com.codibly.schedulerclient;

import com.codibly.schedulerclient.api.JobDescription;
import com.codibly.schedulerclient.api.JobId;
import com.codibly.schedulerclient.api.JobScheduler;
import com.codibly.schedulerclient.api.Schedule;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.verify;

class JobManagementTest extends SharedTestInitializer {

    @Test
    void shouldScheduleJob() {
        // Given
        Instant NOW = CLOCK.instant();
        Instant executionTime = NOW.plus(Duration.ofSeconds(5));
        DummyNotificationDto dummyJobPayload = new DummyNotificationDto("dummyValue");
        JobDescription jobDescription = new JobDescription(
                JobId.from("jobId"),
                dummyJobPayload,
                new Schedule.Fixed(executionTime)
        );

        // When
        jobScheduler.scheduleJob(jobDescription);

        // Then
        JobDto expectedJob = JobDto.builder()
                .name(jobDescription.getJobId().value())
                .schedule("@at %s".formatted(executionTime))
                .executor("http")
                .executor_config(
                        HttpExecutorConfigDto.builder()
                                .url("http://this-application/dkron-webhook/execute-job/%s".formatted(jobDescription.getJobId().value()))
                                .method("POST")
                                .headers("[\"Content-Type: application/json\"]")
                                .body("{\"clazz\":\"com.codibly.schedulerclient.DummyNotificationDto\",\"data\":\"{\\\"field1\\\":\\\"dummyValue\\\"}\"}")
                                .expectCode("200")
                                .build()
                )
                .build();
        verify(dkronRestClient).createOrUpdateJob(
                assertArg(job -> assertThat(job).usingRecursiveComparison().isEqualTo(expectedJob))
        );
    }

    @Test
    void shouldCancelJob() {
        // Given
        JobScheduler jobScheduler = schedulerClientAutoConfiguration.jobScheduler(dkronRestClient);
        JobId jobId = JobId.from("jobId");
        JobDescription jobDescription = new JobDescription(
                jobId,
                new DummyNotificationDto("value"),
                new Schedule.Fixed(CLOCK.instant().plus(Duration.ofSeconds(5)))
        );

        jobScheduler.scheduleJob(jobDescription);

        // When
        jobScheduler.cancelJob(jobId);

        // Then
        verify(dkronRestClient).deleteJob(jobId.value());
    }


}
