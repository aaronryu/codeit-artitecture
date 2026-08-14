package com.example.demo.controller.advice;

import com.example.demo.controller.advice.dto.InvalidParameterDto;
import com.example.demo.exception.CodeitRuntimeException;
import com.example.demo.exception.ExceptionType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.ArrayList;
import java.util.List;

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

    //  (1-1) @Controller 내 메서드에 @Valid + DTO 객체 내 @Min
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
//  @ResponseBody
    public void handle(MethodArgumentNotValidException exception) {
        List<InvalidParameterDto> parameterInvalidDetails = new ArrayList<>();
        for (FieldError eachParameterValidation : exception.getBindingResult().getFieldErrors()) {
            InvalidParameterDto eachParameterInvalidDetail = InvalidParameterDto.builder()
                    .parameter(eachParameterValidation.getField())
                    .actualValue(eachParameterValidation.getRejectedValue())
                    .criteriaValue(eachParameterValidation.getArguments()[1])
                    .criteria(eachParameterValidation.getCode())
                    .violationMessage(eachParameterValidation.getDefaultMessage())
                    .build();
            parameterInvalidDetails.add(eachParameterInvalidDetail);
        }
        log.warn("@RequestBody, @ModelAttribute 으로 받는 요청 DTO 객체 내 검증 실패 값이 존재 : {}", parameterInvalidDetails, exception);
    }

//  (1-2) @Controller 내 메서드에 @Min 그대로 적용 (알아서 처리해줌 - 간단한 객체에 대해)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
//  @ResponseBody
    public void handle(HandlerMethodValidationException exception) {
        List<InvalidParameterDto> parameterInvalidDetails = new ArrayList<>();
        for (ParameterValidationResult eachParameter : exception.getParameterValidationResults()) {
            InvalidParameterDto.InvalidParameterDtoBuilder eachParameterInvalidDetailBuilder = InvalidParameterDto.builder();
            eachParameterInvalidDetailBuilder.parameter(eachParameter.getMethodParameter().getParameterName());
            eachParameterInvalidDetailBuilder.actualValue(eachParameter.getArgument());
            List<MessageSourceResolvable> validations = eachParameter.getResolvableErrors();
            for (MessageSourceResolvable eachValidation : validations) {
                eachParameterInvalidDetailBuilder.criteria(eachValidation.getCodes()[eachValidation.getCodes().length - 1]);
                eachParameterInvalidDetailBuilder.criteriaValue(eachValidation.getArguments()[eachValidation.getArguments().length - 1]);
                String violationMessage = eachValidation.getDefaultMessage();
                eachParameterInvalidDetailBuilder.violationMessage(violationMessage);
            }
            InvalidParameterDto eachParameterInvalidDetail = eachParameterInvalidDetailBuilder.build();
            parameterInvalidDetails.add(eachParameterInvalidDetail);
        }
        log.warn("@PathVariable, @RequestParam 으로 받는 요청 간단한 객체(Integer 등) 내 검증 실패 값이 존재 : {}", parameterInvalidDetails, exception);
    }

//  (2) 아무 클래스에 @Validated + 아무 메서드 내 파라미터에 @Min
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
//  @ResponseBody
    public void handle(ConstraintViolationException exception) {
        List<InvalidParameterDto> parameterInvalidDetails = new ArrayList<>();
        for (ConstraintViolation eachParameterValidation : exception.getConstraintViolations()) {
            InvalidParameterDto eachParameterInvalidDetail = InvalidParameterDto.builder()
                    .parameter(eachParameterValidation.getPropertyPath().toString())
                    .actualValue(eachParameterValidation.getInvalidValue())
//                  .criteriaValue()
//                  .criteria()
                    .violationMessage(eachParameterValidation.getMessage())
                    .build();
            parameterInvalidDetails.add(eachParameterInvalidDetail);
        }
        log.warn("@Service, @Repository 등의 기타 클래스 내 메서드 파라미터들에 대한 검증 실패 값이 존재 : {}", parameterInvalidDetails, exception);
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
