package com.example.demo.advice.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InvalidParameterDto {
    String parameter;           // id
    Object actualValue;         // 0
    Object criteriaValue;       // 1
    String criteria;            // Max
    String violationMessage;    // 1 이상의 값이 들어가야합니다
}

