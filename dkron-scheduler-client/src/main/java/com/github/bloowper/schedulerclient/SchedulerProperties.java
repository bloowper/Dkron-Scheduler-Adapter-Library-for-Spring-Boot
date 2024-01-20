package com.github.bloowper.schedulerclient;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "scheduler")
@Getter
@Setter
@NoArgsConstructor
class SchedulerProperties {
    @NotNull
    private String dkronServerUrl = "http://localhost:8081";
    @Min(1)
    private Integer dkronRestApiRetryAttempts = 3;
    @Min(50)
    private Integer dkronRestApiMinimumBackoffInMilliseconds = 250;
    @NotNull
    private String jobExecutionServerUrl = "http://localhost:8080";
}
