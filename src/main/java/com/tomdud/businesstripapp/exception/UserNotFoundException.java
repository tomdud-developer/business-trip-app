package com.tomdud.businesstripapp.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String string) {
        super(string);
    }
}
