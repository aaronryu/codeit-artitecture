package com.example.demo.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.event.Level;

import java.net.HttpURLConnection;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum ExceptionType implements CodeitExceptionType {
    USER_NOT_FOUND(
            Level.WARN,
            HttpURLConnection.HTTP_NOT_FOUND,
            "찾으시는 유저가 존재하지 않습니다"
    ),
    DATABASE_CONNECTION_FAILED(
            Level.ERROR,
            HttpURLConnection.HTTP_INTERNAL_ERROR,
            "데이터베이스에 접속되지 않는 치명적인 서버 내 오류 발생"
    );

    Level level;
    int status;
    String message;
}
