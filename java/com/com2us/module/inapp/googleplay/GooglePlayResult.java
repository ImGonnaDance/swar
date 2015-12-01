package com.com2us.module.inapp.googleplay;

public class GooglePlayResult {
    String mMessage;
    int mResponse;

    public GooglePlayResult(int response, String message) {
        this.mResponse = response;
        if (message == null || message.trim().length() == 0) {
            this.mMessage = GooglePlayHelper.getResponseDesc(response);
        } else {
            this.mMessage = new StringBuilder(String.valueOf(message)).append(" (response: ").append(GooglePlayHelper.getResponseDesc(response)).append(")").toString();
        }
    }

    public int getResponse() {
        return this.mResponse;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public boolean isSuccess() {
        return this.mResponse == 0;
    }

    public boolean isFailure() {
        return !isSuccess();
    }

    public String toString() {
        return "GooglePlayResult: " + getMessage();
    }
}
