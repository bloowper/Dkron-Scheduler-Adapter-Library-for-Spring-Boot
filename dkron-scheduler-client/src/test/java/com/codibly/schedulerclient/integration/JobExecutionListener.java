package com.codibly.schedulerclient.integration;


import com.codibly.schedulerclient.DummyNotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;

@Slf4j
class JobExecutionListener {
    private List<DummyNotificationDto> dummyNotificationDtos = new ArrayList<>();
    @EventListener
    void onJobExecution(DummyNotificationDto dummyNotificationDto) {
        log.info("Received notification: {}", dummyNotificationDto);
        dummyNotificationDtos.add(dummyNotificationDto);
    }

    public Boolean wasExecuted(DummyNotificationDto dummyNotificationDto, Integer times) {
        return dummyNotificationDtos.stream().filter(dummyNotificationDto::equals).count() == times;
    }

    public void resetExecutionStatus() {
        dummyNotificationDtos = new ArrayList<>();
    }
}
