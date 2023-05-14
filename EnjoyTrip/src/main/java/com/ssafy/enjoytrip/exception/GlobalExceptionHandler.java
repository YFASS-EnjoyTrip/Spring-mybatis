package com.ssafy.enjoytrip.exception;

import com.ssafy.enjoytrip.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseDto(HttpStatus.CONFLICT.value(), ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleIllegalArgumentException(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseDto(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), null));
    }
}
