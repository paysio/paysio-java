package com.paysio.exception;

public class PaysioException extends Exception {

    private static final long serialVersionUID = -932932770162784237L;

    public PaysioException() {
    }

    public PaysioException(String message) {
        super(message);
    }

    public PaysioException(Throwable cause) {
        super(cause);
    }

    public PaysioException(String message, Throwable cause) {
        super(message, cause);
    }

}
