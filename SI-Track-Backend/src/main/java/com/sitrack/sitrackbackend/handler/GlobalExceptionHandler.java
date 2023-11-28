package com.sitrack.sitrackbackend.handler;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().stream()
                .forEach(a -> {
                    String fieldName = ((FieldError)a).getField();
                    String message = a.getDefaultMessage();
                    errors.put(fieldName, message);
                });
        map.put("statusCode", HttpStatus.BAD_REQUEST);
        map.put("status", status.value());
        map.put("errors", errors);

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    // 숫자 null, 공백, 잘못된 타입 오류
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("code", "ERR5001");
        error.put("message", "Required request body is missing");

        return ResponseEntity.badRequest().body(error);
    }


}
