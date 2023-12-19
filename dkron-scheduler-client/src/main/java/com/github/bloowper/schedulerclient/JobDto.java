package com.github.bloowper.schedulerclient;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/*
 * reade more about dkron job here: https://dkron.io/api/#operation/createOrUpdateJob
 *
 */
@Data
@Builder
final class JobDto {
    private final @NotNull String name;
    private final @Nullable String displayname;
    private final @NotNull String schedule;
    private final @Nullable String timezone;
    private final @Nullable String owner;
    private final @Nullable Boolean disabled;
    private final @Nullable Integer retries;
    private final @Nullable String parent_job;
    private final @Nullable Boolean concurrency;
    private final @Nullable String executor;
    private final @Nullable ExecutorConfig executor_config;


    JobDto(@NotNull String name, @Nullable String displayname, @NotNull String schedule, @Nullable String timezone, @Nullable String owner, @Nullable Boolean disabled, @Nullable Integer retries, @Nullable String parent_job, @Nullable Boolean concurrency, @Nullable String executor, @Nullable ExecutorConfig executor_config) {
        if (ObjectUtils.anyNull(name, schedule)) {
            throw new IllegalArgumentException("name and schedule are required");
        }
        this.name = name;
        this.displayname = displayname;
        this.schedule = schedule;
        this.timezone = timezone;
        this.owner = owner;
        this.disabled = disabled;
        this.retries = retries;
        this.parent_job = parent_job;
        this.concurrency = concurrency;
        this.executor = executor;
        this.executor_config = executor_config;
    }

}
