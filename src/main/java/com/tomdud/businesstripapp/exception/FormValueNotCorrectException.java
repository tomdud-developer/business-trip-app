package com.tomdud.businesstripapp.exception;

public class FormValueNotCorrectException extends RuntimeException{
    public FormValueNotCorrectException(String string) {
        super(string);
    }
}
