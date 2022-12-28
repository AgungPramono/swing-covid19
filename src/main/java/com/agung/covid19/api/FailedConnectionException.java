package com.agung.covid19.api;

public class FailedConnectionException extends Exception{
    public FailedConnectionException(String message) {
        super(message);
    }
}
