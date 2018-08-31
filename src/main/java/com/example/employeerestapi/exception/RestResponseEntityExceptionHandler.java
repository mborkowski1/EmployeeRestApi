package com.example.employeerestapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { EntityNotFoundException.class, NoResultException.class })
    public ResponseEntity<Object> handleNotFound(final Exception ex, final WebRequest request) {
        final String response = ex.getMessage();
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { EntityExistsException.class })
    public ResponseEntity<Object> handleConflict(final Exception ex, final WebRequest request) {
        final String response = ex.getMessage();
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = { NullPointerException.class })
    public ResponseEntity<Object> handleInternal(final Exception ex, final WebRequest request) {
        final String response = ex.getMessage();
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
