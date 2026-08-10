package com.example.demo.application.user;

import com.example.demo.controller.admin.api.dto.UserAdminResponseDto;
import com.example.demo.controller.admin.api.dto.UserAdminUpdateRequestDto;
import com.example.demo.repository.user.User;
import com.example.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAdminApplication {
    private final UserService userService;

    public List<UserAdminResponseDto> retrieve() {
        List<User> users = userService.getUsers();
        return users.stream()
                .map(UserAdminResponseDto::from)
                .toList();
    }

    public UserAdminResponseDto retrieve(Integer id) {
        User retrieved = userService.getUser(id);
        return UserAdminResponseDto.from(retrieved);
    }

    public UserAdminResponseDto create(User entity) {
        User created = userService.create(entity);
        return UserAdminResponseDto.from(created);
    }

    public UserAdminResponseDto update(Integer id, UserAdminUpdateRequestDto request) {
        User updating = userService.getUser(id);
        updating.update(request.getName(), request.getGrade(), request.getPoint());
        User updated = userService.update(updating);
        return UserAdminResponseDto.from(updated);
    }

    public void active(Integer id) {
        userService.active(id);
    }

    public void softDelete(Integer id) {
        userService.softDelete(id);
    }

    public void hardDelete(Integer id) {
        userService.hardDelete(id);
    }
}
