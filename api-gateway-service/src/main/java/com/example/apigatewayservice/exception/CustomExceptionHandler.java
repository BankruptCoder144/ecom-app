package com.example.apigatewayservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@ControllerAdvice
@ResponseBody
public class CustomExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<String>> handleValidateException(RuntimeException ex, ServerWebExchange exchange) {
        System.out.println("an error occured " + ex.getMessage());
        return Mono.just(ResponseEntity
                .status(HttpStatus.FORBIDDEN).body("Only User can perform this action"));
    }
}
