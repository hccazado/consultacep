package com.byu.consultacep.exceptions;

public class InvalidDataException extends Throwable{
    final private String message;

    public InvalidDataException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
