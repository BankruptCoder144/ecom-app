package com.example.productservice.exceptions;

import com.example.productservice.controller.ProductController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackageClasses = { ProductController.class })
@ResponseBody
public class AppExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(AppException.class)
    public ResponseEntity<ExceptionResult> handleThrowable(AppException ex) {
        return new ResponseEntity<>(new ExceptionResult(ex), ex.getErrorCode());

    }
}
