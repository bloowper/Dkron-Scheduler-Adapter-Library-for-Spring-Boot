package com.github.bloowper.schedulerclient.common;

import lombok.Getter;

import java.time.Duration;
import java.time.Instant;

@Getter
public class TestStopper {
    private final Instant start;
    private Instant end;

    public TestStopper(Instant start) {
        this.start = start;
    }

    public static TestStopper start(){
        return new TestStopper(Instant.now());
    }

    public TestStopper stop(){
        end = Instant.now();
        return this;
    }

    public Duration getElapsedTime(){
        return Duration.between(start, end);
    }
}
