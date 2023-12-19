package com.github.bloowper.schedulerclient;

import com.github.bloowper.schedulerclient.api.JobDescription;
import com.github.bloowper.schedulerclient.api.JobId;
import com.github.bloowper.schedulerclient.api.JobScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponentsBuilder;

import static com.github.bloowper.schedulerclient.JobNotificationPayloadSerializer.serialize;

@RequiredArgsConstructor
@Slf4j
class JobSchedulerImpl implements JobScheduler {
    private static final String EXECUTOR = "http";
    private static final String METHOD = "POST";
    private final DkronRestClient dkronRestClient;
    private final UriComponentsBuilder jobExecutionUriComponentsBuilder;
    public JobId scheduleJob(JobDescription jobDescription) {
        log.info("Scheduling job {}", jobDescription);
        String jobId = jobDescription.getJobId().value();
        String executeJobUrl = DkronWebhookWebController.createUriToExecuteJob(
                jobExecutionUriComponentsBuilder,
                jobId
        ).toString();
        String body = serialize(jobDescription.getJobNotificationPayload());
        dkronRestClient.createOrUpdateJob(
                JobDto.builder()
                        .name(jobId)
                        .schedule(jobDescription.getSchedule().scheduleExpression())
                        .executor(EXECUTOR)
                        .executor_config(
                                HttpExecutorConfigDto.builder()
                                        .url(executeJobUrl)
                                        .method(METHOD)
                                        .headers("[\"Content-Type: application/json\"]")
                                        .body(body)
                                        .expectCode("200")
                                        .build()
                        )
                        .build()
        );
        return jobDescription.getJobId();
    }

    @Override
    public void cancelJob(JobId jobId) {
        log.info("Cancelling job {}", jobId);
        dkronRestClient.deleteJob(jobId.value());
    }
}
