package com.brainspark.pulsereport.platform.handover.domain.exceptions;


public class InvalidHandoverException extends RuntimeException {

    public InvalidHandoverException(String reason) {
        super(String.format("Invalid handover: %s", reason));
    }
}
