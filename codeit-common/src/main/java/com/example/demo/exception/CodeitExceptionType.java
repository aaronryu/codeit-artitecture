package com.example.demo.exception;

import org.slf4j.event.Level;

public interface CodeitExceptionType {
    Level getLevel();
    int getStatus();
    String getMessage();
}
