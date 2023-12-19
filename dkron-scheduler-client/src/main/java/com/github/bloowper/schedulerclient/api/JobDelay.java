package com.github.bloowper.schedulerclient.api;

import java.time.Duration;

public final class JobDelay {
    // Allow delay job execution by specified duration
    // Also allow delay job execution to specific datetime
    private final Duration delay;

    public JobDelay(Duration delay) {
        this.delay = delay;
    }
}
