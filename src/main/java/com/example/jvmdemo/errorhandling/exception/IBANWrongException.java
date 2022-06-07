package com.example.jvmdemo.errorhandling.exception;

public class IBANWrongException extends RuntimeException {

    public IBANWrongException(final String message) {
        super(message);
    }
}
