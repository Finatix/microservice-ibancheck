package com.example.jvmdemo.errorhandling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IBANWrongException extends RuntimeException {

    public IBANWrongException(final String message) {
        super(message);
    }
}
