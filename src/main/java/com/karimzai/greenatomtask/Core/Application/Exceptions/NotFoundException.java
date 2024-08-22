package com.karimzai.greenatomtask.Core.Application.Exceptions;

public class NotFoundException extends Exception {
    public NotFoundException(String name, Object key) {
        super(String.format("{%s} (%s) не найден.", name, key));
    }
}
