package com.github.bloowper.schedulerclient.integration;


import com.github.bloowper.schedulerclient.DummyNotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
class JobExecutionListener {
    private List<DummyNotificationDto> dummyNotificationDtos = new ArrayList<>();
    private List<Instant> executionDates = new ArrayList<>(30);
    @EventListener
    void onJobExecution(DummyNotificationDto dummyNotificationDto) {
        Instant now = Instant.now();
        log.info("Received notification: {} at: {}", dummyNotificationDto,now);
        dummyNotificationDtos.add(dummyNotificationDto);
        executionDates.add(now);
    }

    public Boolean wasExecutedWith(DummyNotificationDto dummyNotificationDto, Integer times) {
        return dummyNotificationDtos.stream().filter(dummyNotificationDto::equals).count() == times;
    }

    public Boolean wasExecutedWith(DummyNotificationDto dummyNotificationDto){
        return dummyNotificationDtos.stream().anyMatch(dummyNotificationDto::equals);
    }

    Instant getFirstExecutionDate(){
        return executionDates.getFirst();
    }

}
