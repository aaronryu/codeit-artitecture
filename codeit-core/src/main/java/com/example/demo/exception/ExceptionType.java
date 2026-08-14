package com.example.demo.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum ExceptionType {
    USER_NOT_FOUND("찾으시는 유저가 존재하지 않습니다"),
    DATABASE_CONNECTION_FAILED("데이터베이스에 접속되지 않는 치명적인 서버 내 오류 발생");

    String message;
}
