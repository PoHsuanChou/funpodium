package com.example.demo.controller;

import com.example.demo.error.BtcException;
import com.example.demo.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(BtcException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse AuthenticationFailException(Exception e){
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.toString());
        error.setMessage(e.getMessage());
        return error;
    }
}
