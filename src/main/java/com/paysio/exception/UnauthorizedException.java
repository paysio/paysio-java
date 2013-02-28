package com.paysio.exception;

@SuppressWarnings("serial")
public class UnauthorizedException extends PaysioException {

    public UnauthorizedException(String message) {
        super(message);
    }

}
