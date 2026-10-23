package com.example.fooddelivery.exception;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
@RestControllerAdvice
public class ApiExceptionHandler {
 @ExceptionHandler(ResponseStatusException.class) public ResponseEntity<Map<String,Object>> handleStatus(ResponseStatusException ex){return ResponseEntity.status(ex.getStatusCode()).body(Map.of("error",ex.getReason()==null?"Request failed":ex.getReason()));}
 @ExceptionHandler(MethodArgumentNotValidException.class) public ResponseEntity<Map<String,Object>> handleValidation(MethodArgumentNotValidException ex){String msg=ex.getBindingResult().getFieldErrors().stream().map(e->e.getField()+": "+e.getDefaultMessage()).findFirst().orElse("Invalid request");return ResponseEntity.badRequest().body(Map.of("error",msg));}
 @ExceptionHandler(Exception.class) public ResponseEntity<Map<String,Object>> handleOther(Exception ex){return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error","Unexpected server error"));}
}
