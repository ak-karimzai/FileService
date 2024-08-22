package com.karimzai.greenatomtask.Core.Application.Responses;

import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;

import java.util.ArrayList;
import java.util.List;

public class BaseResponse {
    private boolean success;
    private String message;
    private List<String> validationErrors;

    public BaseResponse() {
        success = true;
    }

    public BaseResponse(String message) {
        success = true;
        this.message = message;
    }

    public BaseResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public BaseResponse(ValidationResult validationResult) {
        if (!validationResult.getErrors().isEmpty()) {
            success = false;
            validationErrors = new ArrayList<>();
            for (Error error : validationResult.getErrors()) {
                validationErrors.add(error.getMessage());
            }
        } else {
            success = true;
        }
    }

    public void addError(String error) {
        if (validationErrors == null) {
            validationErrors = new ArrayList<>();
        }
        validationErrors.add(error);
        success = false;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
