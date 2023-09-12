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
    private final JobScheduler jobScheduler;


    @PostMapping
    String scheduleJob(){
        String id = UUID.randomUUID().toString();
        JobDescription jobDescription = new JobDescription(
                id,
                new PrintSomethingJobDto("Job %s execution".formatted(id)),
                new Schedule.Fixed(Instant.now().plus(Duration.ofSeconds(5)))
        );
        jobScheduler.scheduleJob(jobDescription);
        return id;
    }

}
