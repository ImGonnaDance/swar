package com.com2us.module.inapp.googleplay;

public class GooglePlayException extends Exception {
    GooglePlayResult mResult;

    public GooglePlayException(GooglePlayResult r) {
        this(r, null);
    }

    public GooglePlayException(int response, String message) {
        this(new GooglePlayResult(response, message));
    }

    public GooglePlayException(GooglePlayResult r, Exception cause) {
        super(r.getMessage(), cause);
        this.mResult = r;
    }

    public GooglePlayException(int response, String message, Exception cause) {
        this(new GooglePlayResult(response, message), cause);
    }

    public GooglePlayResult getResult() {
        return this.mResult;
    }
}
