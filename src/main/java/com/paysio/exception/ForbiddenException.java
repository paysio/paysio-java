package com.paysio.exception;

@SuppressWarnings("serial")
public class ForbiddenException extends PaysioException {

    public ForbiddenException(String message) {
        super(message);
    }

}
