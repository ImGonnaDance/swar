package com.facebook;

import com.facebook.android.R;
import com.facebook.internal.Utility;
import java.net.HttpURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public final class FacebookRequestError {
    private static final String BODY_KEY = "body";
    private static final String CODE_KEY = "code";
    private static final int EC_APP_NOT_INSTALLED = 458;
    private static final int EC_APP_TOO_MANY_CALLS = 4;
    private static final int EC_EXPIRED = 463;
    private static final int EC_INVALID_SESSION = 102;
    private static final int EC_INVALID_TOKEN = 190;
    private static final int EC_PASSWORD_CHANGED = 460;
    private static final int EC_PERMISSION_DENIED = 10;
    private static final Range EC_RANGE_PERMISSION = new Range(SelectTarget.TARGETING_SUCCESS, 299);
    private static final int EC_SERVICE_UNAVAILABLE = 2;
    private static final int EC_UNCONFIRMED_USER = 464;
    private static final int EC_UNKNOWN_ERROR = 1;
    private static final int EC_USER_CHECKPOINTED = 459;
    private static final int EC_USER_TOO_MANY_CALLS = 17;
    private static final String ERROR_CODE_FIELD_KEY = "code";
    private static final String ERROR_CODE_KEY = "error_code";
    private static final String ERROR_IS_TRANSIENT_KEY = "is_transient";
    private static final String ERROR_KEY = "error";
    private static final String ERROR_MESSAGE_FIELD_KEY = "message";
    private static final String ERROR_MSG_KEY = "error_msg";
    private static final String ERROR_REASON_KEY = "error_reason";
    private static final String ERROR_SUB_CODE_KEY = "error_subcode";
    private static final String ERROR_TYPE_FIELD_KEY = "type";
    private static final String ERROR_USER_MSG_KEY = "error_user_msg";
    private static final String ERROR_USER_TITLE_KEY = "error_user_title";
    private static final Range HTTP_RANGE_CLIENT_ERROR = new Range(400, 499);
    private static final Range HTTP_RANGE_SERVER_ERROR = new Range(500, 599);
    private static final Range HTTP_RANGE_SUCCESS = new Range(SelectTarget.TARGETING_SUCCESS, 299);
    public static final int INVALID_ERROR_CODE = -1;
    public static final int INVALID_HTTP_STATUS_CODE = -1;
    private static final int INVALID_MESSAGE_ID = 0;
    private final Object batchRequestResult;
    private final Category category;
    private final HttpURLConnection connection;
    private final int errorCode;
    private final boolean errorIsTransient;
    private final String errorMessage;
    private final String errorType;
    private final String errorUserMessage;
    private final String errorUserTitle;
    private final FacebookException exception;
    private final JSONObject requestResult;
    private final JSONObject requestResultBody;
    private final int requestStatusCode;
    private final boolean shouldNotifyUser;
    private final int subErrorCode;
    private final int userActionMessageId;

    public enum Category {
        AUTHENTICATION_RETRY,
        AUTHENTICATION_REOPEN_SESSION,
        PERMISSION,
        SERVER,
        THROTTLING,
        OTHER,
        BAD_REQUEST,
        CLIENT
    }

    private static class Range {
        private final int end;
        private final int start;

        private Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        boolean contains(int value) {
            return this.start <= value && value <= this.end;
        }
    }

    private FacebookRequestError(int requestStatusCode, int errorCode, int subErrorCode, String errorType, String errorMessage, String errorUserTitle, String errorUserMessage, boolean errorIsTransient, JSONObject requestResultBody, JSONObject requestResult, Object batchRequestResult, HttpURLConnection connection, FacebookException exception) {
        this.requestStatusCode = requestStatusCode;
        this.errorCode = errorCode;
        this.subErrorCode = subErrorCode;
        this.errorType = errorType;
        this.errorMessage = errorMessage;
        this.requestResultBody = requestResultBody;
        this.requestResult = requestResult;
        this.batchRequestResult = batchRequestResult;
        this.connection = connection;
        this.errorUserTitle = errorUserTitle;
        this.errorUserMessage = errorUserMessage;
        this.errorIsTransient = errorIsTransient;
        boolean isLocalException = false;
        if (exception != null) {
            this.exception = exception;
            isLocalException = true;
        } else {
            this.exception = new FacebookServiceException(this, errorMessage);
        }
        Category errorCategory = null;
        int messageId = 0;
        if (isLocalException) {
            errorCategory = Category.CLIENT;
            messageId = 0;
        } else {
            if (errorCode == EC_UNKNOWN_ERROR || errorCode == EC_SERVICE_UNAVAILABLE) {
                errorCategory = Category.SERVER;
            } else if (errorCode == EC_APP_TOO_MANY_CALLS || errorCode == EC_USER_TOO_MANY_CALLS) {
                errorCategory = Category.THROTTLING;
            } else if (errorCode == EC_PERMISSION_DENIED || EC_RANGE_PERMISSION.contains(errorCode)) {
                errorCategory = Category.PERMISSION;
                messageId = R.string.com_facebook_requesterror_permissions;
            } else if (errorCode == EC_INVALID_SESSION || errorCode == EC_INVALID_TOKEN) {
                if (subErrorCode == EC_USER_CHECKPOINTED || subErrorCode == EC_UNCONFIRMED_USER) {
                    errorCategory = Category.AUTHENTICATION_RETRY;
                    messageId = R.string.com_facebook_requesterror_web_login;
                } else {
                    errorCategory = Category.AUTHENTICATION_REOPEN_SESSION;
                    messageId = (subErrorCode == EC_APP_NOT_INSTALLED || subErrorCode == EC_EXPIRED) ? R.string.com_facebook_requesterror_relogin : subErrorCode == EC_PASSWORD_CHANGED ? R.string.com_facebook_requesterror_password_changed : R.string.com_facebook_requesterror_reconnect;
                }
            }
            if (errorCategory == null) {
                if (HTTP_RANGE_CLIENT_ERROR.contains(requestStatusCode)) {
                    errorCategory = Category.BAD_REQUEST;
                } else if (HTTP_RANGE_SERVER_ERROR.contains(requestStatusCode)) {
                    errorCategory = Category.SERVER;
                } else {
                    errorCategory = Category.OTHER;
                }
            }
        }
        boolean shouldNotify = errorUserMessage != null && errorUserMessage.length() > 0;
        this.category = errorCategory;
        this.userActionMessageId = messageId;
        this.shouldNotifyUser = shouldNotify;
    }

    private FacebookRequestError(int requestStatusCode, int errorCode, int subErrorCode, String errorType, String errorMessage, String errorUserTitle, String errorUserMessage, boolean errorIsTransient, JSONObject requestResultBody, JSONObject requestResult, Object batchRequestResult, HttpURLConnection connection) {
        this(requestStatusCode, errorCode, subErrorCode, errorType, errorMessage, errorUserTitle, errorUserMessage, errorIsTransient, requestResultBody, requestResult, batchRequestResult, connection, null);
    }

    FacebookRequestError(HttpURLConnection connection, Exception exception) {
        this(INVALID_HTTP_STATUS_CODE, INVALID_HTTP_STATUS_CODE, INVALID_HTTP_STATUS_CODE, null, null, null, null, false, null, null, null, connection, exception instanceof FacebookException ? (FacebookException) exception : new FacebookException((Throwable) exception));
    }

    public FacebookRequestError(int errorCode, String errorType, String errorMessage) {
        this(INVALID_HTTP_STATUS_CODE, errorCode, INVALID_HTTP_STATUS_CODE, errorType, errorMessage, null, null, false, null, null, null, null, null);
    }

    public int getUserActionMessageId() {
        return this.userActionMessageId;
    }

    public boolean shouldNotifyUser() {
        return this.shouldNotifyUser;
    }

    public Category getCategory() {
        return this.category;
    }

    public int getRequestStatusCode() {
        return this.requestStatusCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public int getSubErrorCode() {
        return this.subErrorCode;
    }

    public String getErrorType() {
        return this.errorType;
    }

    public String getErrorMessage() {
        if (this.errorMessage != null) {
            return this.errorMessage;
        }
        return this.exception.getLocalizedMessage();
    }

    public String getErrorUserMessage() {
        return this.errorUserMessage;
    }

    public String getErrorUserTitle() {
        return this.errorUserTitle;
    }

    public boolean getErrorIsTransient() {
        return this.errorIsTransient;
    }

    public JSONObject getRequestResultBody() {
        return this.requestResultBody;
    }

    public JSONObject getRequestResult() {
        return this.requestResult;
    }

    public Object getBatchRequestResult() {
        return this.batchRequestResult;
    }

    public HttpURLConnection getConnection() {
        return this.connection;
    }

    public FacebookException getException() {
        return this.exception;
    }

    public String toString() {
        return "{HttpStatus: " + this.requestStatusCode + ", errorCode: " + this.errorCode + ", errorType: " + this.errorType + ", errorMessage: " + getErrorMessage() + "}";
    }

    static FacebookRequestError checkResponseAndCreateError(JSONObject singleResult, Object batchResult, HttpURLConnection connection) {
        try {
            if (singleResult.has(ERROR_CODE_FIELD_KEY)) {
                int responseCode = singleResult.getInt(ERROR_CODE_FIELD_KEY);
                Object body = Utility.getStringPropertyAsJSON(singleResult, BODY_KEY, Response.NON_JSON_RESPONSE_PROPERTY);
                if (body != null && (body instanceof JSONObject)) {
                    JSONObject jsonBody = (JSONObject) body;
                    String errorType = null;
                    String errorMessage = null;
                    String errorUserMessage = null;
                    String errorUserTitle = null;
                    boolean errorIsTransient = false;
                    int errorCode = INVALID_HTTP_STATUS_CODE;
                    int errorSubCode = INVALID_HTTP_STATUS_CODE;
                    boolean hasError = false;
                    if (jsonBody.has(ERROR_KEY)) {
                        JSONObject error = (JSONObject) Utility.getStringPropertyAsJSON(jsonBody, ERROR_KEY, null);
                        errorType = error.optString(ERROR_TYPE_FIELD_KEY, null);
                        errorMessage = error.optString(ERROR_MESSAGE_FIELD_KEY, null);
                        errorCode = error.optInt(ERROR_CODE_FIELD_KEY, INVALID_HTTP_STATUS_CODE);
                        errorSubCode = error.optInt(ERROR_SUB_CODE_KEY, INVALID_HTTP_STATUS_CODE);
                        errorUserMessage = error.optString(ERROR_USER_MSG_KEY, null);
                        errorUserTitle = error.optString(ERROR_USER_TITLE_KEY, null);
                        errorIsTransient = error.optBoolean(ERROR_IS_TRANSIENT_KEY, false);
                        hasError = true;
                    } else if (jsonBody.has(ERROR_CODE_KEY) || jsonBody.has(ERROR_MSG_KEY) || jsonBody.has(ERROR_REASON_KEY)) {
                        errorType = jsonBody.optString(ERROR_REASON_KEY, null);
                        errorMessage = jsonBody.optString(ERROR_MSG_KEY, null);
                        errorCode = jsonBody.optInt(ERROR_CODE_KEY, INVALID_HTTP_STATUS_CODE);
                        errorSubCode = jsonBody.optInt(ERROR_SUB_CODE_KEY, INVALID_HTTP_STATUS_CODE);
                        hasError = true;
                    }
                    if (hasError) {
                        return new FacebookRequestError(responseCode, errorCode, errorSubCode, errorType, errorMessage, errorUserTitle, errorUserMessage, errorIsTransient, jsonBody, singleResult, batchResult, connection);
                    }
                }
                if (!HTTP_RANGE_SUCCESS.contains(responseCode)) {
                    JSONObject jSONObject;
                    if (singleResult.has(BODY_KEY)) {
                        jSONObject = (JSONObject) Utility.getStringPropertyAsJSON(singleResult, BODY_KEY, Response.NON_JSON_RESPONSE_PROPERTY);
                    } else {
                        jSONObject = null;
                    }
                    return new FacebookRequestError(responseCode, INVALID_HTTP_STATUS_CODE, INVALID_HTTP_STATUS_CODE, null, null, null, null, false, jSONObject, singleResult, batchResult, connection);
                }
            }
        } catch (JSONException e) {
        }
        return null;
    }
}
