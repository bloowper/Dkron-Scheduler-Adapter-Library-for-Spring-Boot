package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Create POJO for job execution, this will be used to notify about job execution
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
class PrintSomethingJobDto {
    private String message;
}
