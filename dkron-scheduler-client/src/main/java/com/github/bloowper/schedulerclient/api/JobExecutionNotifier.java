package com.github.bloowper.schedulerclient.api;


/**
 * Defines a contract for notifying when a job starts its execution.
 * Implementations of this interface should handle the process of
 * distributing the job execution notification to relevant parties.
 * <p>
 * Default implementation publishes the notifications as spring boot event.
 * To catch default implementation events, use {@link org.springframework.context.event.EventListener} annotation.
 * In default implementation only jobNotificationPayload is published not the whole {@link JobExecutionNotification}.
 * </p>
 */
public interface JobExecutionNotifier {
    void notifyJobExecution(JobExecutionNotification jobExecutionNotification);
}
