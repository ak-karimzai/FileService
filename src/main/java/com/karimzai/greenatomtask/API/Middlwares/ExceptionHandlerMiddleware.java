package com.karimzai.greenatomtask.API.Middlwares;

import com.karimzai.greenatomtask.API.Models.ErrorResponse;
import com.karimzai.greenatomtask.Core.Application.Exceptions.BadRequestException;
import com.karimzai.greenatomtask.Core.Application.Exceptions.NotFoundException;
import com.karimzai.greenatomtask.Core.Application.Exceptions.ServiceUnavailableException;
import com.karimzai.greenatomtask.Core.Application.Exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ExceptionHandlerMiddleware extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<List<String>> handleValidationException(ValidationException e, WebRequest request) {
        return new ResponseEntity<>(e.getValidationErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleServiceUnavailableException(ServiceUnavailableException e, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
