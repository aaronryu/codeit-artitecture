package com.example.demo.exception;

import lombok.Getter;

@Getter
public class CodeitRuntimeException extends RuntimeException {
    private final ExceptionType type;

    public CodeitRuntimeException(ExceptionType type) {
        super(type.getMessage());
        this.type = type;
    }
}
