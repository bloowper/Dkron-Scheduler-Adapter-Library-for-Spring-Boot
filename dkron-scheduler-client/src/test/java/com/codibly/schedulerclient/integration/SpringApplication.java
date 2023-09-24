package com.codibly.schedulerclient.integration;

import com.codibly.schedulerclient.EnableDkronScheduler;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDkronScheduler
class SpringApplication {
    // If remove this class, the integration test will fail due to context problems ( there will not be any context )
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }
}
