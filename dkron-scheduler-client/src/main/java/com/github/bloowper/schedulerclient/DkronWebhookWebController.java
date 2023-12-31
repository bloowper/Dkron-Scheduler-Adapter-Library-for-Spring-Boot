package com.github.bloowper.schedulerclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import java.net.URI;


@RestController
@RequestMapping("/dkron-webhook")
@RequiredArgsConstructor
@Slf4j
class DkronWebhookWebController {
    private final JobExecutionNotificationService jobExecutionNotificationService;

    @PostMapping("/execute-job/{jobId}")
    void executeJob(@RequestBody String body, @PathVariable String jobId) {
        log.debug("Received request to execute job with id: {}", jobId);
        jobExecutionNotificationService.handleJobExecution(body, jobId);
    }

    /**
     * @return URI to execute job with given id, this doesn't include host and scheme part(uri path only)
     */
    static URI createUriToExecuteJob(UriComponentsBuilder uriComponentsBuilder, String jobId) {
        String uri = new UriTemplate("/dkron-webhook/execute-job/{jobId}").expand(jobId).toString();
        return uriComponentsBuilder.path(uri).build(jobId);
    }
}
