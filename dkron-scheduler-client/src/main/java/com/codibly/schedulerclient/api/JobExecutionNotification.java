package com.codibly.schedulerclient.api;

/**
 * The notification contains an ID and a payload, which are set by the {@link JobDescription}.
 */
public record JobExecutionNotification(String id,Object jobNotificationPayload) {
}
