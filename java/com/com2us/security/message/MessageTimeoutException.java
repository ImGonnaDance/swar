package com.com2us.security.message;

public class MessageTimeoutException extends Exception {
    private static final long serialVersionUID = -7096247908533997772L;

    public MessageTimeoutException(String message) {
        super(message);
    }

    public MessageTimeoutException(Throwable cause) {
        super(cause);
    }

    public MessageTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
