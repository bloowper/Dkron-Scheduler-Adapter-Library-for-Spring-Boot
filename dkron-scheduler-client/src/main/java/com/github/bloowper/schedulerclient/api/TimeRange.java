package com.github.bloowper.schedulerclient.api;

import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Optional;

public class TimeRange {
    @Nullable
    private final Instant start;
    @Nullable
    private final Instant end;

    public TimeRange(@Nullable Instant start, @Nullable Instant end) {
        if(start != null && end != null && start.isAfter(end)) {
            throw new IllegalArgumentException("Start have to be smaller than end");
        }
        this.start = start;
        this.end = end;
    }

    TimeRange withStart(Instant start) {
        return new TimeRange(start, this.end);
    }

    TimeRange withEnd(Instant end) {
        return new TimeRange(this.start, end);
    }

    static TimeRange start(Instant start){
        return new TimeRange(start, null);
    }

    static TimeRange end(Instant end){
        return new TimeRange(null, end);
    }

    static TimeRange infinite(){
        return new TimeRange(null, null);
    }

    Optional<Instant> getStart(){
        return Optional.ofNullable(start);
    }

    Optional<Instant> getEnd(){
        return Optional.ofNullable(end);
    }
}
