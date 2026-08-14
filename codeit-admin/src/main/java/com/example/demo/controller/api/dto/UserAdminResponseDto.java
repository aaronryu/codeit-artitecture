package com.example.demo.controller.api.dto;

import com.example.demo.repository.user.User;
import com.example.demo.repository.user.UserGrade;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserAdminResponseDto {
    private final Integer id;
    private final String name;
    private final UserGrade grade;
    private final int point;
    private final String thumbnail;
    private final boolean deleted;

    public static UserAdminResponseDto from(User entity) {
        return new UserAdminResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getGrade(),
                entity.getPoint(),
                entity.getThumbnail(),
                entity.isDeleted()
        );
    }
}
