package com.example.demo.controller.admin.api.dto;

import com.example.demo.controller.internal.api.dto.RequestingUserDto;
import com.example.demo.repository.user.User;
import com.example.demo.repository.user.UserGrade;
import lombok.Getter;

@Getter
public class UserAdminUpdateRequestDto extends RequestingUserDto {
    private final String name;
    private final UserGrade grade;
    private final int point;

    public UserAdminUpdateRequestDto(String name, UserGrade grade, int point, Integer requestUserId) {
        super(requestUserId);
        this.name = name;
        this.grade = grade;
        this.point = point;
    }
}
