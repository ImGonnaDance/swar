package com.com2us.module.crypt;

public class MaxBytesExceededException extends RuntimeCryptoException {
    public MaxBytesExceededException(String message) {
        super(message);
    }
}
