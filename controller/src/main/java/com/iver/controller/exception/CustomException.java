package com.iver.controller.exception;

public class CustomException extends RuntimeException {
    public CustomException(String message, Throwable cause) {
        super(customMessage(message), cause);
    }

    public CustomException(String message) {
        super(customMessage(message));
    }

    private static String customMessage(String message) {
        return "Custom exception: " + message;
    }
}
