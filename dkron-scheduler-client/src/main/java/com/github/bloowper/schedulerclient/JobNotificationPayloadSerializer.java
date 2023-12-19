package com.github.bloowper.schedulerclient;

import com.github.bloowper.schedulerclient.api.JobSchedulingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class JobNotificationPayloadSerializer {
    //TODO optimize
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static String serialize(Object object) {
        try {
            DataWithClassInfo dataWithClassInfo = new DataWithClassInfo(
                    object.getClass().getName(),
                    objectMapper.writeValueAsString(object)
            );
            return objectMapper.writeValueAsString(dataWithClassInfo);
        } catch (JsonProcessingException e) {
            throw new JobSchedulingException(e);
        }
    }

    static Object deserialize(String json) {
        try {
            DataWithClassInfo dataWithClassInfo = objectMapper.readValue(json, DataWithClassInfo.class);
            Class<?> clazz = Class.forName(dataWithClassInfo.clazz());
            return objectMapper.readValue(dataWithClassInfo.data(), clazz);
        } catch (JsonProcessingException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private record DataWithClassInfo(String clazz, String data) {
    }
}
