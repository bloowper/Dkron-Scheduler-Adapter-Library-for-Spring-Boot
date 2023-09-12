package com.codibly.schedulerclient;

import com.codibly.schedulerclient.api.JobExecutionNotification;
import com.codibly.schedulerclient.api.JobExecutionNotifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
@Slf4j
class DefaultJobExecutionNotifier implements JobExecutionNotifier {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void notifyJobExecution(JobExecutionNotification jobExecutionNotification) {
        log.debug("Publishing job execution notification: {}", jobExecutionNotification);
        applicationEventPublisher.publishEvent(jobExecutionNotification.jobNotificationPayload());
    }
}
