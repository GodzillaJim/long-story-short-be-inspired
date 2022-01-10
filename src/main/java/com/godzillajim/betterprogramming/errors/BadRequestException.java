package com.godzillajim.betterprogramming.errors;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
