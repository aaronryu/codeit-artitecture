package com.example.demo.controller.advice;

import com.example.demo.exception.CodeitRuntimeException;
import com.example.demo.exception.ExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestControllerAdvice
// @RestControllerAdvice = @ControllerAdvice + @ResponseBody
// @RestController       = @Controller       + @ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(CodeitRuntimeException.class)
//  @ResponseBody
    public ResponseEntity<Void> handle(CodeitRuntimeException exception) {
        // 내가 알고있거나 / 명시적으로 처리하고싶어하는 예외 상황에 대해 이렇게 구체적인 예외 클래스를 명시해서 처리
        ExceptionType exceptionType = exception.getType();
        log.makeLoggingEventBuilder(exceptionType.getLevel())
                .setCause(exception)
                .log(exception.getMessage());
        return ResponseEntity
                .status(exceptionType.getStatus())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
//  @ResponseBody
    public void handle(Exception exception /* 클래스 다형성에 의해 우리가 만드는 예외 Exception 들이 모두 Exception 상속받기에 여기로 다 들어옴 */) {
        // 세상에는 (라이브러리, 프레임워크 등) 너무 다양한 예외들이 존재하기에 우리가 catch 하지 못하고 놓친 예외에 대해 꼭 마지막까지 처리해줘야한다
        // = switch 구문에서 default 와 거의 같은 목적의 코드라고 보면 된다
        log.error("우리가 커버하지 못한 예외 발생", exception);
    }
}
