package com.com2us.module.newsbanner2;

public class NetworkDataException extends Exception {
    private static final long serialVersionUID = 310974572301002240L;

    public NetworkDataException(String string) {
        super(string);
    }

    public NetworkDataException(Throwable throwable) {
        super(throwable);
    }
}
