package com.example.userservice.exceptions;

import com.example.userservice.controllers.UserController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackageClasses = { UserController.class })
@ResponseBody
public class AppExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(AppException.class)
    public ResponseEntity<ExceptionResult> handleThrowable(AppException ex) {
        return new ResponseEntity<>(new ExceptionResult(ex), ex.getErrorCode());

    }
}
