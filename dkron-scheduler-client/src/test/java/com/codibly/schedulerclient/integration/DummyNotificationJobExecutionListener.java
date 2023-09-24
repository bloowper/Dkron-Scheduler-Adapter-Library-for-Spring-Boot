package com.codibly.schedulerclient.integration;


import com.codibly.schedulerclient.DummyNotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@Slf4j
class DummyNotificationJobExecutionListener {
    private Boolean executed = false;
    @EventListener
    void onJobExecution(DummyNotificationDto dummyNotificationDto) {
        log.info("Received notification: {}", dummyNotificationDto);
        executed = true;
    }

    public Boolean wasExecuted() {
        return executed;
    }

    public void resetExecutionStatus() {
        executed = false;
    }
}
