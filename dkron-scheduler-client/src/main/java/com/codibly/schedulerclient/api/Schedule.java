package com.codibly.schedulerclient.api;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public sealed interface Schedule {
    String scheduleExpression();
    /**
     * Schedule for specifying a job schedule based on a Cron expression.
     *
     * @param expression A valid Cron expression as per Dkron specifications.
     */
    record Cron(String expression) implements Schedule {
        public Cron {
            if (expression.isBlank()) {
                throw new IllegalArgumentException("Cron expression cannot be blank");
            }
            // TODO valid cron validation
        }

        @Override
        public String scheduleExpression() {
            return expression;
        }
    }

    /**
     * Schedule for specifying a job schedule based on a fixed time interval.
     *
     * @param interval A positive Duration object that represents the time interval between each job execution.
     *                 At least 1 second
     */
    record Interval(Duration interval) implements Schedule {
        public Interval {
            if (interval.isNegative()) {
                throw new IllegalArgumentException("Interval must be positive");
            }
            if (interval.getSeconds() < 1) {
                throw new IllegalArgumentException("Interval must be at least 1 second");
            }
        }

        @Override
        public String scheduleExpression() {
            return "@every %ss".formatted(interval.getSeconds());
        }
    }

    /**
     * Schedule for specifying a job that will run once at a fixed instant in time.
     *
     * @param at Instant that represents the fixed point in time when the job will be executed.
     */
    record Fixed(Instant at) implements Schedule {
        private static final DateTimeFormatter RFC3339_FORMATTER = DateTimeFormatter
                .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .withZone(java.time.ZoneOffset.UTC);
        @Override
        public String scheduleExpression() {
            return "@at %s".formatted(RFC3339_FORMATTER.format(at));
        }
    }
}
