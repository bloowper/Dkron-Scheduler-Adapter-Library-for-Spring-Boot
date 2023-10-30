package org.example;

import com.codibly.schedulerclient.api.JobDescription;
import com.codibly.schedulerclient.api.JobId;
import com.codibly.schedulerclient.api.JobScheduler;
import com.codibly.schedulerclient.api.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
class SchedulingController {
    // Inject job scheduler, provided by library
    private final JobScheduler jobScheduler;

    @PostMapping
    JobId scheduleJob(){
        // Create job description
        JobDescription jobDescription = new JobDescription(
                new PrintSomethingJobDto("Job execution"),
                new Schedule.Fixed(Instant.now().plus(Duration.ofSeconds(5)))
        );

        // Schedule job
        return jobScheduler.scheduleJob(jobDescription);
    }

}
