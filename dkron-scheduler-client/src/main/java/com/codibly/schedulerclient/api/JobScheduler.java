package com.codibly.schedulerclient.api;

public interface JobScheduler {
    /**
     * Schedules a job for execution based on the provided {@link JobDescription}.
     * time according to its schedule. Upon job execution, a notification should be
     * sent via {@link JobExecutionNotifier#notifyJobExecution(JobExecutionNotification)}.
     * If job with this id already exist, it will be overwritten.
     */
    void scheduleJob(JobDescription jobDescription) throws JobSchedulingException;

    /**
     * Cancels a scheduled job based on its identifier.
     * If job not exist, no exception is thrown.
     */
    void cancelJob(String jobId);
}
