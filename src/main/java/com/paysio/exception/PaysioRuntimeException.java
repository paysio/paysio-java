package com.paysio.exception;

public class PaysioRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 3084968256567592495L;

    public PaysioRuntimeException() {
    }

    public PaysioRuntimeException(String message) {
        super(message);
    }

    public PaysioRuntimeException(Throwable cause) {
        super(cause);
    }

    public PaysioRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
