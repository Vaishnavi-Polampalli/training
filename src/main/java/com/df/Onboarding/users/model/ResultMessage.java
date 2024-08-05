package com.df.Onboarding.users.model;

public class ResultMessage {
    public boolean success;
    public String message;

    public ResultMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResultMessage() {

    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
