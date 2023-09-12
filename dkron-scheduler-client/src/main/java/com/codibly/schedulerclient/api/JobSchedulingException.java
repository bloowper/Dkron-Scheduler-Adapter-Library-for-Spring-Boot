package com.codibly.schedulerclient.api;

public class JobSchedulingException extends RuntimeException {
    public JobSchedulingException(String message) {
        super(message);
    }

    public JobSchedulingException(Throwable cause) {
        super(cause);
    }
}
