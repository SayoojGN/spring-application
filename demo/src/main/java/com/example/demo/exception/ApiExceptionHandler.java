package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public APIException handleApiRequestExecution(ApiRequestException e){
        HttpStatus httpStatus = null;
        if(e.getMessage() == "Employee added"){
            httpStatus = HttpStatus.CREATED;
        }
        else if(e.getMessage() == "Employee deleted"){
            httpStatus = HttpStatus.NO_CONTENT;
        }
        else if(e.getMessage() == "Employee not found"){
            httpStatus = HttpStatus.NOT_FOUND;
        }
        else if(e.getMessage() == "Employee already exists") {
            httpStatus = HttpStatus.CONFLICT;
        }
        else if(e.getMessage() == "Employee updated"){
            httpStatus = HttpStatus.OK;
        }
        return new APIException(
                e.getMessage(),
                e,
                httpStatus,
                ZonedDateTime.now());
    }
}
