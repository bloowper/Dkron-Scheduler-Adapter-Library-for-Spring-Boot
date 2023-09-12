package com.codibly.schedulerclient;

import com.codibly.schedulerclient.api.JobExecutionNotifier;
import com.codibly.schedulerclient.api.JobScheduler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
@EnableConfigurationProperties(SchedulerProperties.class)
class SchedulerClientAutoConfiguration {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final SchedulerProperties schedulerProperties;

    public SchedulerClientAutoConfiguration(ApplicationEventPublisher applicationEventPublisher,
                                            SchedulerProperties schedulerProperties) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.schedulerProperties = schedulerProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    JobExecutionNotifier jobExecutionNotifier() {
        return new DefaultJobExecutionNotifier(applicationEventPublisher);
    }

    @Bean
    JobExecutionNotificationService jobExecutionNotificationService(JobExecutionNotifier jobExecutionNotifier) {
        return new JobExecutionNotificationService(jobExecutionNotifier);
    }

    @Bean
    DkronRestClient dkronRestClient() {
        WebClient.Builder builder = WebClient.builder();
        WebClient webClient = builder
                .baseUrl(schedulerProperties.getDkronServerUrl())
                .build();
        return new DkronRestClient(webClient,schedulerProperties.getDkronRestApiRetryAttempts(),schedulerProperties.getDkronRestApiMinimumBackoffInMilliseconds());
    }

    @Bean
    JobScheduler jobScheduler(DkronRestClient dkronRestClient) {
        String jobExecutionServerUrl = schedulerProperties.getJobExecutionServerUrl();
        UriComponentsBuilder jobExecutionUriComponentsBuilder = UriComponentsBuilder.fromUriString(jobExecutionServerUrl);
        return new JobSchedulerImpl(dkronRestClient,jobExecutionUriComponentsBuilder);
    }

    @Bean
    DkronWebhookWebController dkronWebhookWebController(JobExecutionNotificationService jobExecutionNotificationService) {
        return new DkronWebhookWebController(jobExecutionNotificationService);
    }
}
