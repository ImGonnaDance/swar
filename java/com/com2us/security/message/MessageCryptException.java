package com.com2us.security.message;

public class MessageCryptException extends Exception {
    private static final long serialVersionUID = -2665701169429027625L;

    public MessageCryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageCryptException(String message) {
        super(message);
    }

    public MessageCryptException(Throwable cause) {
        super(cause);
    }
}
