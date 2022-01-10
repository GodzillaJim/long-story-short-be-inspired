package com.godzillajim.betterprogramming.errors;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class ErrorResponse {
    Date timestamp = new Date();
    int code;
    String status;
    String message;
    String stackTrace;
    Object data;
}
