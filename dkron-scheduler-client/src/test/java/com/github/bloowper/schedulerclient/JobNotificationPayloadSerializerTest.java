package com.github.bloowper.schedulerclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JobNotificationPayloadSerializerTest {

    private static final TestObject TEST_OBJECT = new TestObject("dummyValue");
    private static final String TEST_OBJECT_JSON = "{\"clazz\":\"com.github.bloowper.schedulerclient.JobNotificationPayloadSerializerTest$TestObject\",\"data\":\"{\\\"someField\\\":\\\"dummyValue\\\"}\"}";
    private static final TestRecord TEST_RECORD = new TestRecord("dummyValue");
    private static final String TEST_RECORD_JSON = "{\"clazz\":\"com.github.bloowper.schedulerclient.JobNotificationPayloadSerializerTest$TestRecord\",\"data\":\"{\\\"someField\\\":\\\"dummyValue\\\"}\"}";

    @Test
    void shouldMarshallPojo() {
        // when
        String result = JobNotificationPayloadSerializer.serialize(TEST_OBJECT);

        // then
        assertThat(result).isEqualTo(TEST_OBJECT_JSON);
    }

    @Test
    void shouldUnmarshallPojo() {
        // when
        Object result = JobNotificationPayloadSerializer.deserialize(TEST_OBJECT_JSON);

        // then
        assertThat(result).isEqualTo(TEST_OBJECT);
    }

    @Test
    void shouldMarshallRecord() {
        // when
        String result = JobNotificationPayloadSerializer.serialize(TEST_RECORD);

        // then
        assertThat(result).isEqualTo(TEST_RECORD_JSON);
    }

    @Test
    void shouldUnmarshallRecord() {
        // when
        Object result = JobNotificationPayloadSerializer.deserialize(TEST_RECORD_JSON);

        // then
        assertThat(result).isEqualTo(TEST_RECORD);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TestObject {
        private String someField;
    }

    private record TestRecord(String someField) {
    }
}
