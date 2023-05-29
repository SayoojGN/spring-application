package com.example.demo.exception;

import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestExecution(ApiRequestException e){
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
        APIException apiException = new APIException(
                e.getMessage(),
                e,
                httpStatus,
                ZonedDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }


}
