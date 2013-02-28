package com.paysio.exception;

@SuppressWarnings("serial")
public class NotFoundException extends PaysioException {

    public NotFoundException(String message) {
        super(message);
    }

}
