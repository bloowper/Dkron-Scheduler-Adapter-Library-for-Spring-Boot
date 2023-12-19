package com.github.bloowper.schedulerclient.api;

/**
 * The notification contains an ID and a payload, which are set by the {@link JobDescription}.
 */
public record JobExecutionNotification(JobId id, Object jobNotificationPayload) {
}
