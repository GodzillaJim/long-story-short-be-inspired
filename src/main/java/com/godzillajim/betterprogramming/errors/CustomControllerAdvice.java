package com.godzillajim.betterprogramming.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

@ControllerAdvice
public class CustomControllerAdvice {
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(Exception e){
        return getErrorResponseResponseEntity(e);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(Exception e){
        return getErrorResponseResponseEntity(e);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception e){
        ErrorResponse response = new ErrorResponse();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        response.setCode(status.value());
        response.setMessage(e.getMessage());
        response.setStatus(status.name());
        response.setStackTrace(stringifyStacktrace(e));
        return new ResponseEntity<>(
                response,status
        );
    }

    private ResponseEntity<ErrorResponse> getErrorResponseResponseEntity(Exception e) {
        ErrorResponse response = new ErrorResponse();
        HttpStatus status = HttpStatus.NOT_FOUND;
        response.setCode(status.value());
        response.setMessage(e.getMessage());
        response.setStatus(status.name());
        response.setStackTrace(stringifyStacktrace(e));
        return new ResponseEntity<>(
                response,status
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleRemainingExceptions(Exception e){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();
        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(new Date());
        response.setStatus(status.name());
        response.setMessage(e.getMessage());
        response.setStackTrace(stackTrace);
        response.setData(e.getCause());
        return new ResponseEntity<>(response,status);
    }
    private String stringifyStacktrace(Exception e){
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
//TODO: Implement Search Blog feature
