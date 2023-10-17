package com.codibly.schedulerclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;

@Slf4j
class DkronRestClient {
    private final WebClient dkronWebClient;
    private final RetryBackoffSpec retrySpec;

    public DkronRestClient(WebClient dkronWebClient, Integer dkronRestApiRetryAttempts, Integer dkronRestApiMinimumBackoffInMilliseconds) {
        this.dkronWebClient = dkronWebClient;
        this.retrySpec = Retry.backoff(dkronRestApiRetryAttempts, Duration.ofMillis(dkronRestApiMinimumBackoffInMilliseconds));
    }

    void createOrUpdateJob(JobDto job) {
        dkronWebClient.post()
                .uri("/v1/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(job)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(throwable -> log.error("Creating job failed: {}", job, throwable))
                .doOnSuccess(unused -> log.debug("Successfully created job: {}", job))
                .retryWhen(retrySpec)
                .block();
    }

    void deleteJob(String jobId) {
        dkronWebClient.delete()
                .uri("/v1/jobs/{jobId}", jobId)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(throwable -> log.error("Creating job failed: {}", jobId, throwable))
                .doOnSuccess(unused -> log.debug("Successfully deleted job: {}", jobId))
                .retryWhen(retrySpec)
                .block();
    }
}

