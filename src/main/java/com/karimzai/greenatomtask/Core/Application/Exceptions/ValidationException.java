package com.karimzai.greenatomtask.Core.Application.Exceptions;

import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends Exception {
    private List<String> validationErrors;

    public ValidationException(ValidationResult validationResult) {
        validationErrors = new ArrayList<>();
        for (Error error : validationResult.getErrors()) {
            validationErrors.add(error.getMessage());
        }
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }
}