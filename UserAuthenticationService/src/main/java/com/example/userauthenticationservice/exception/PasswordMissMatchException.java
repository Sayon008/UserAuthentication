package com.example.userauthenticationservice.exception;

public class PasswordMissMatchException extends Exception {
    public PasswordMissMatchException(String message) {
        super(message);
    }
}
