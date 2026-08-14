package com.example.demo.exception;

public class DatabaseConnectionException extends RuntimeException {
    public DatabaseConnectionException() {
        super("데이터베이스에 접속되지 않는 치명적인 서버 내 오류 발생");
    }
}
