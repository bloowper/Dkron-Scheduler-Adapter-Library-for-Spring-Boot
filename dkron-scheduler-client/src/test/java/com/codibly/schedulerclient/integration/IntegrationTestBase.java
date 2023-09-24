package com.codibly.schedulerclient.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.testcontainers.Testcontainers.exposeHostPorts;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
@TestPropertySource(
        properties = {
                "scheduler.dkron-server-url=http://localhost:8081",
                "scheduler.job-execution-server-url=http://host.docker.internal:8080",
                "server.port=8080"

        }
)
class IntegrationTestBase {
    @Container
    public static GenericContainer<?> dkronContainer = new FixedHostPortGenericContainer<>("dkron/dkron:3.2.3")
            .withFixedExposedPort(8081, 8080) // map dkron port 8080 to host port 8081
            .withCommand("agent --server --bootstrap-expect=1 --node-name=node1 --data-dir=/dkron.data --log-level debug")
            .waitingFor(Wait.forHttp("/v1/jobs"))
            .withEnv("DKRON_SINGLE", "true")
            .withEnv("GODEBUG", "netdns=go")
            .withExtraHost("host.docker.internal", "host-gateway");

    static {
        exposeHostPorts(8080);
    }
}
