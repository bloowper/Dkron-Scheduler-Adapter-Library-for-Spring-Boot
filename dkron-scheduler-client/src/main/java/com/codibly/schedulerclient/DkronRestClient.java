package com.codibly.schedulerclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;

@Slf4j
class DkronRestClient {
    //TODO: integrate tests with dkron
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
                .doOnError(throwable -> log.error("Error while creating job: {}", job, throwable))
                .retryWhen(retrySpec)
                .block();
    }

    void deleteJob(String jobId) {
        dkronWebClient.delete()
                .uri("/v1/jobs/{jobId}", jobId)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(throwable -> log.error("Error while deleting job: {}", jobId, throwable))
                .retryWhen(retrySpec)
                .block();
    }
}

