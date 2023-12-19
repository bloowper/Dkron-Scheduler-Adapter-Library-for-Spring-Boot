package com.github.bloowper.schedulerclient;

import com.github.bloowper.schedulerclient.api.JobExecutionNotification;
import com.github.bloowper.schedulerclient.api.JobId;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
class JobExecutionTest extends SharedTestInitializer{


    @Test
    void shouldNotifyAboutJobExecution() {
        // Given
        String jobJson = "{\"clazz\":\"com.github.bloowper.schedulerclient.DummyNotificationDto\",\"data\":\"{\\\"field1\\\":\\\"dummyValue\\\"}\"}";
        String jobId = "jobId";

        // When
        jobExecutionNotificationService.handleJobExecution(jobJson,jobId);

        // Then
        JobExecutionNotification expectedNotification = new JobExecutionNotification(new JobId(jobId), new DummyNotificationDto("dummyValue"));
        verify(jobExecutionNotifier).notifyJobExecution(expectedNotification);
    }
}
