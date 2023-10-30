package com.codibly.schedulerclient;

import com.codibly.schedulerclient.api.JobDescription;
import com.codibly.schedulerclient.api.JobId;
import com.codibly.schedulerclient.api.Schedule;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class JobSchedulerTest extends SharedTestInitializer {

    @Test
    void shouldReturnAutoGeneratedJobId() {
        // given
        JobDescription jobDescription = new JobDescription(
                "notification",
                Schedule.fixed(Instant.now())
        );

        // when
        JobId jobId = jobScheduler.scheduleJob(jobDescription);

        // then
        assertThat(jobId).isNotNull();
    }

    @Test
    void shouldReturnBusinessJobId() {
        // given
        String businessId = "businessId";
        JobDescription jobDescription = new JobDescription(
                JobId.from(businessId),
                "notification",
                Schedule.fixed(Instant.now())
        );

        // when
        JobId jobId = jobScheduler.scheduleJob(jobDescription);

        // then
        assertThat(jobId.value()).isEqualTo(businessId);
    }
}