package com.godzillajim.betterprogramming.errors;

public class UnauthorizedRequestException extends RuntimeException{
    public UnauthorizedRequestException(String message){
        super(message);
    }
}
