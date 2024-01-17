package com.github.bloowper.schedulerclient;

import com.github.bloowper.schedulerclient.api.JobExecutionNotifier;
import com.github.bloowper.schedulerclient.api.JobScheduler;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.mockito.Mockito.mock;

public class SharedTestInitializer {

    protected static final Instant NOW = Instant.parse("2021-01-01T00:00:00.00Z");
    protected static final Clock CLOCK = Clock.fixed(NOW, ZoneOffset.UTC);
    protected final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    protected final DkronRestClient dkronRestClient = mock(DkronRestClient.class);
    protected final JobExecutionNotifier jobExecutionNotifier = mock(JobExecutionNotifier.class);
    protected final SchedulerProperties schedulerProperties = new SchedulerProperties("http://localhost:8081", "http://this-application", 3, 250);
    protected final SchedulerClientAutoConfiguration schedulerClientAutoConfiguration = new SchedulerClientAutoConfiguration(
            applicationEventPublisher,
            schedulerProperties
    );
    protected final JobScheduler jobScheduler = schedulerClientAutoConfiguration.jobScheduler(dkronRestClient);
    protected final JobExecutionNotificationService jobExecutionNotificationService = schedulerClientAutoConfiguration.jobExecutionNotificationService(jobExecutionNotifier);

}
