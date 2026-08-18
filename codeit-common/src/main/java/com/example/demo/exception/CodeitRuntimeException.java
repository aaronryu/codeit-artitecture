package com.example.demo.exception;

import lombok.Getter;

@Getter
public class CodeitRuntimeException extends RuntimeException {
    private final CodeitExceptionType type;

    public CodeitRuntimeException(CodeitExceptionType type) {
        super(type.getMessage());
        this.type = type;
    }
}
