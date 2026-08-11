package com.example.demo.repository.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum UserGrade {
    BRONZE(0.01),
    SILVER(0.02),
    GOLD(0.03),
    PLATINUM(0.05);

    double earningRate;
}