package com.github.bloowper.schedulerclient;

import com.github.bloowper.schedulerclient.api.JobExecutionNotification;
import com.github.bloowper.schedulerclient.api.JobExecutionNotifier;
import com.github.bloowper.schedulerclient.api.JobId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class JobExecutionNotificationService {
    private final JobExecutionNotifier jobExecutionNotifier;

    void handleJobExecution(String json, String jobId) {
        Object jobNotificationPayload = JobNotificationPayloadSerializer.deserialize(json);
        jobExecutionNotifier.notifyJobExecution(new JobExecutionNotification(new JobId(jobId), jobNotificationPayload));
    }
}
