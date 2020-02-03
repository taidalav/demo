package com.example.demo.controller;

import com.example.demo.exception.AgentNotFoundException;
import com.example.demo.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

  @ResponseBody
  @ExceptionHandler(value = ValidationException.class)
  public ResponseEntity<?> handleValidationException(ValidationException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMsg());
  }

  @ResponseBody
  @ExceptionHandler(value = AgentNotFoundException.class)
  public ResponseEntity<?> handleAgentNotFoundException(AgentNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMsg());
  }
}
