package com.example.demo.error;

public class BtcException extends RuntimeException {
    public BtcException() {
        super();
    }

    public BtcException(String message) {
        super(message);
    }

    public BtcException(String message, Throwable cause) {
        super(message, cause);
    }
}
