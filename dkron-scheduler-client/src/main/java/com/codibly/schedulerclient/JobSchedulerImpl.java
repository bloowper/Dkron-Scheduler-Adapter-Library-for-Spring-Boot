package com.codibly.schedulerclient;

import com.codibly.schedulerclient.api.JobDescription;
import com.codibly.schedulerclient.api.JobScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponentsBuilder;

import static com.codibly.schedulerclient.JobNotificationPayloadSerializer.serialize;

@RequiredArgsConstructor
@Slf4j
class JobSchedulerImpl implements JobScheduler {
    private static final String EXECUTOR = "http";
    private static final String METHOD = "POST";
    private final DkronRestClient dkronRestClient;
    private final UriComponentsBuilder jobExecutionUriComponentsBuilder;
    public void scheduleJob(JobDescription jobDescription) {
        log.info("Scheduling job {}", jobDescription);
        String executeJobUrl = DkronWebhookWebController.createUriToExecuteJob(jobExecutionUriComponentsBuilder, jobDescription.id()).toString();
        String body = serialize(jobDescription.jobNotificationPayload());
        dkronRestClient.createOrUpdateJob(
                JobDto.builder()
                        .name(jobDescription.id())
                        .schedule(jobDescription.schedule().scheduleExpression())
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
    }

    @Override
    public void cancelJob(String jobId) {
        log.info("Cancelling job {}", jobId);
        dkronRestClient.deleteJob(jobId);
    }
}
