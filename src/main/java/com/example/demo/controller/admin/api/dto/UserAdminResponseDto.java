package com.example.demo.controller.admin.api.dto;

import com.example.demo.repository.user.User;
import com.example.demo.repository.user.UserGrade;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAdminResponseDto {
    private final Integer id;
    private final String name;
    private final UserGrade grade;
    private final int point;
    private final boolean deleted;

    public static UserAdminResponseDto from(User entity) {
        return new UserAdminResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getGrade(),
                entity.getPoint(),
                entity.isDeleted()
        );
    }
}
