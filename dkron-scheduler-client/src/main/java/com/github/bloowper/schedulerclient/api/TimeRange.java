package com.github.bloowper.schedulerclient.api;

import org.jetbrains.annotations.Nullable;

import java.time.Instant;

public interface TimeRange {
    @Nullable
    Instant startOfTimeRange();
    @Nullable
    Instant endOfTimeRange();
}
