package io.ermdev.cshop.data.model;

public class Error {

    private String message;

    public Error() {}

    public Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
