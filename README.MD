# Dkron Scheduler Adapter Library for Spring Boot

The Dkron Scheduler Adapter Library is a powerful, user-friendly Spring Boot library, designed as an adapter for the
Dkron tool. This library provides convenient job scheduling and execution capabilities within your Spring Boot
applications.

# Installation

- To integrate the Dkron Scheduler Client Adapter Library in your Spring Boot project add to gradle

```gradle
TODO :)
```

- Eventually [configure properties](#configuration-properties)

# Configuration properties

- `scheduler.dkron-server-url` : URL of the dkron server
    * default : `http://localhost:8081`
    * (if you are running dkron server on docker remember to map 8080 to 8081,the default port configuration has been
      changed because it is the default one for the Spring application.)
- `scheduler.dkron-rest-api-retry-attempts` : Number of attempts to retry the rest api call to dkron server
    * default : `3`
- `scheduler.dkron-rest-api-minimum-backoff-in-milliseconds` : Minimum backoff time in milliseconds between retries
    * default : `250`
- `scheduler.job-execution-server-url` : URL of the job execution server, in other words URL of application in network,
  so that dkron can find it and execute the job.
    * default : `http://localhost:8080`

# Usage

## Scheduling job

To schedule jobs, use the JobScheduler bean and pass an instance of JobDescription to it. Here's a concise example of
scheduling a job,

```java

@Component
@RequiredArgsConstructor
class SchedulingExample {
    private final JobScheduler jobScheduler;

    void scheduleJob() {
        String id = UUID.randomUUID().toString();
        JobDescription jobDescription = new JobDescription(
                id,
                new PrintSomethingJobDto("Job %s execution".formatted(id)),
                new Schedule.Fixed(Instant.now().plus(Duration.ofSeconds(5)))
        );
        jobScheduler.scheduleJob(jobDescription);
    }

}
```

### additional information about JobDescription

`jobNotificationPayload`: A POJO (Plain Old Java Object) containing additional information sent as part of the job
execution notification. This object can be any DTO but must adhere to the POJO structure, containing a no-args
constructor, getters, and setters.

## Handling Job Executions

### Default Notification Publisher

By default, this library uses Spring Boot’s event publisher for notifying about job executions. To handle these
notifications, create a method annotated with @EventListener and listen for the object passed to JobDescription.

```java

@Component
@Slf4j
class JobExecutionListener {
    @EventListener
    public void execute(PrintSomethingJobDto printSomethingJobDto) {
        log.info("PrintSomething job executed: {}", printSomethingJobDto);
    }
}
```

### Custom Notification Handling

For custom implementations, provide your own implementation of JobExecutionNotifier.
