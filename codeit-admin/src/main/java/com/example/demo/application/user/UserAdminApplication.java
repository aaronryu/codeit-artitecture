package com.example.demo.application.user;

import com.example.demo.controller.api.dto.UserAdminCreateRequestDto;
import com.example.demo.controller.api.dto.UserAdminResponseDto;
import com.example.demo.controller.api.dto.UserAdminUpdateRequestDto;
import com.example.demo.repository.user.User;
import com.example.demo.service.user.UserService;
import com.example.demo.utils.MultipartFileUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserAdminApplication {
    private final UserService userService;
    private final MultipartFileUpload multipartFileUpload;

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

    public UserAdminResponseDto create(UserAdminCreateRequestDto request, MultipartFile thumbnail) {
        User creating = request.to();
        if (Objects.nonNull(thumbnail)) {
            String thumbnailPath = multipartFileUpload.upload(thumbnail);
            creating.upload(thumbnailPath);
        }
        User created = userService.create(creating);
        return UserAdminResponseDto.from(created);
    }

    public UserAdminResponseDto update(Integer id, UserAdminUpdateRequestDto request, MultipartFile thumbnail) {
        User updating = userService.getUser(id);
        updating.update(request.getName(), request.getGrade(), request.getPoint());
        if (Objects.nonNull(thumbnail)) {
            String thumbnailPath = multipartFileUpload.upload(thumbnail);
            updating.upload(thumbnailPath);
        }
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
