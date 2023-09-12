package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class JobExecutionListener {

    @EventListener
    public void execute(PrintSomethingJobDto printSomethingJobDto) {
        log.info("PrintSomething job executed: {}", printSomethingJobDto);
    }
}
