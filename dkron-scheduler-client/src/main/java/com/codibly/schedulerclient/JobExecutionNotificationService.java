package com.codibly.schedulerclient;

import com.codibly.schedulerclient.api.JobExecutionNotification;
import com.codibly.schedulerclient.api.JobExecutionNotifier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class JobExecutionNotificationService {
    private final JobExecutionNotifier jobExecutionNotifier;

    void handleJobExecution(String json, String jobId) {
        Object jobNotificationPayload = JobNotificationPayloadSerializer.deserialize(json);
        jobExecutionNotifier.notifyJobExecution(new JobExecutionNotification(jobId, jobNotificationPayload));
    }
}
