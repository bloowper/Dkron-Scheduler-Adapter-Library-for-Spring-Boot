package org.example;

import com.github.bloowper.schedulerclient.JobNotificationPayloadCompatibilityChecker;
import org.junit.jupiter.api.Test;

class PojoCompatibilityTest {


    // Create test for checking if given pojo can be used as a job payload
    @Test
    void  checkIfPojoIsCompatibleWithDkronScheduler(){
        // Given
        PrintSomethingJobDto helloWorld = new PrintSomethingJobDto("Hello World");

        // Then
        JobNotificationPayloadCompatibilityChecker.of(helloWorld).verifySerializationCompatibility();
    }
}
