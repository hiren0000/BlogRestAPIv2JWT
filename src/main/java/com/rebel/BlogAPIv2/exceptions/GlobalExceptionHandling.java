package com.rebel.BlogAPIv2.exceptions;

import com.rebel.BlogAPIv2.payloads.ApiResponse;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandling
{
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> ResourceNotFoundException(ResourceNotFoundException rx)
    {
        String message = rx.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validationException(MethodArgumentNotValidException ex)
    {
        Map<String, String> respo = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            respo.put(field, message);
        });

        return new ResponseEntity<Map<String, String>>(respo, HttpStatus.BAD_REQUEST);

    }

}
