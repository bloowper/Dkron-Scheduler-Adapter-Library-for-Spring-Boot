package com.codibly.schedulerclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JobNotificationPayloadSerializerTest {

    private static final TestObject TEST_OBJECT = new TestObject("dummyValue");
    private static final String TEST_OBJECT_JSON = "{\"clazz\":\"com.codibly.schedulerclient.JobNotificationPayloadSerializerTest$TestObject\",\"data\":\"{\\\"someField\\\":\\\"dummyValue\\\"}\"}";

    @Test
    void shouldMarshall() {
        // when
        String result = JobNotificationPayloadSerializer.serialize(TEST_OBJECT);

        // then
        assertThat(result).isEqualTo(TEST_OBJECT_JSON);
    }

    @Test
    void shouldUnmarshall() {
        // when
        Object result = JobNotificationPayloadSerializer.deserialize(TEST_OBJECT_JSON);

        // then
        assertThat(result).isEqualTo(TEST_OBJECT);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TestObject {
        private String someField;
    }

}
