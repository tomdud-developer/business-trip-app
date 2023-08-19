package com.tomdud.businesstripapp.exception;

public class UserNotAuthenticatedException extends RuntimeException{
    public UserNotAuthenticatedException(String string) {
        super(string);
    }
}
