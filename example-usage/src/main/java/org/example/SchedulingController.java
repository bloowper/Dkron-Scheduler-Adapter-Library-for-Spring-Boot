package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codibly.schedulerclient.api.*;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
class SchedulingController {
    // Inject job scheduler, provided by library
    private final JobScheduler jobScheduler;

    @PostMapping
    String scheduleJob(){
        // Generate unique id for job, need to be unique across dkron cluster
        String id = UUID.randomUUID().toString();
        // Create job description
        JobDescription jobDescription = new JobDescription(
                id,
                new PrintSomethingJobDto("Job %s execution".formatted(id)),
                new Schedule.Fixed(Instant.now().plus(Duration.ofSeconds(5)))
        );
        // Schedule job
        jobScheduler.scheduleJob(jobDescription);
        return id;
    }

}
