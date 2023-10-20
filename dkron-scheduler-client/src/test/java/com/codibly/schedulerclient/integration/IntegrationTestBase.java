package com.codibly.schedulerclient.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.testcontainers.Testcontainers.exposeHostPorts;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@Testcontainers
@Slf4j
class IntegrationTestBase {
    private static final Integer SERVER_PORT = 8083;
    private static final Integer DKRON_PORT = 8080;
    private static final GenericContainer<?> DKRON_CONTAINER = new GenericContainer<>(DockerImageName.parse("dkron/dkron:3.2.3"))
            .withExposedPorts(DKRON_PORT)
            .withCommand("agent --server --bootstrap-expect=1")
            .withLogConsumer(outputFrame -> log.info(outputFrame.getUtf8String()))
            .withAccessToHost(true);

    static {
        exposeHostPorts(SERVER_PORT);
        DKRON_CONTAINER.start();
    }

    @TestConfiguration
    static class IntegrationTestBaseConfiguration {
        @Bean
        JobExecutionListener dummyNotificationJobExecutionListener() {
            return new JobExecutionListener();
        }
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("server.port", () -> SERVER_PORT);
        registry.add("scheduler.job-execution-server-url", () -> String.format("http://host.testcontainers.internal:%d", SERVER_PORT));
        registry.add("scheduler.dkron-server-url", () -> String.format("http://%s:%d", DKRON_CONTAINER.getHost(), DKRON_CONTAINER.getMappedPort(DKRON_PORT)));
    }
}
