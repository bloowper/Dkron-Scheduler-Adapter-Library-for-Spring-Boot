package com.codibly.schedulerclient;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

@ConfigurationProperties(prefix = "scheduler")
@Getter
class SchedulerProperties {
    @NotNull
    private final String dkronServerUrl;
    @Min(1)
    private final Integer dkronRestApiRetryAttempts;
    @Min(50)
    private final Integer dkronRestApiMinimumBackoffInMilliseconds;
    @NotNull
    private final String jobExecutionServerUrl;

    @ConstructorBinding
    public SchedulerProperties(String dkronServerUrl,
                               String jobExecutionServerUrl,
                               Integer dkronRestApiRetryAttempts,
                               Integer dkronRestApiMinimumBackoffInMilliseconds) {
        this.dkronServerUrl = getOrDefault(dkronServerUrl, "http://localhost:8081");
        this.jobExecutionServerUrl = getOrDefault(jobExecutionServerUrl, "http://localhost:8080");
        this.dkronRestApiRetryAttempts = getOrDefault(dkronRestApiRetryAttempts,3);
        this.dkronRestApiMinimumBackoffInMilliseconds = getOrDefault(dkronRestApiMinimumBackoffInMilliseconds, 250);
    }

    private <T> T getOrDefault(T value, T defaultValue) {
        return Optional.ofNullable(value).orElse(defaultValue);
    }
    
}
