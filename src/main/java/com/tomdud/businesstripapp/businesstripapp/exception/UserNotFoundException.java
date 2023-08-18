package com.tomdud.businesstripapp.businesstripapp.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String string) {
        super(string);
    }
}
