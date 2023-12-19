package com.github.bloowper.schedulerclient.api;

import io.netty.util.internal.StringUtil;

public record JobId(String value) {
    public JobId {
        if (StringUtil.isNullOrEmpty(value)) {
            throw new IllegalArgumentException("JobId cannot be null");
        }
    }

    public static JobId from(String value) {
        return new JobId(value);
    }

    public static JobId autoGenerated() {
        return new JobId(java.util.UUID.randomUUID().toString());
    }
}