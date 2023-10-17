package com.codibly.schedulerclient;

import com.codibly.schedulerclient.api.JobExecutionNotification;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
class JobExecutionTest extends SharedTestInitializer{


    @Test
    void shouldNotifyAboutJobExecution() {
        // Given
        String jobJson = "{\"clazz\":\"com.codibly.schedulerclient.DummyNotificationDto\",\"data\":\"{\\\"field1\\\":\\\"dummyValue\\\"}\"}";
        String jobId = "jobId";

        // When
        jobExecutionNotificationService.handleJobExecution(jobJson,jobId);

        // Then
        JobExecutionNotification expectedNotification = new JobExecutionNotification(jobId, new DummyNotificationDto("dummyValue"));
        verify(jobExecutionNotifier).notifyJobExecution(expectedNotification);
    }
}