package com.constantine.ordercapture.exception;

public class ServerError {

    private final String url;
    private final String error;

    public ServerError(String url, Exception ex) {
        this.url = url;
        this.error = ex.getMessage();
    }
}
