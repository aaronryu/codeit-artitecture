package com.example.demo.controller.admin.api;

import com.example.demo.application.user.UserAdminApplication;
import com.example.demo.common.context.UserContext;
import com.example.demo.controller.admin.api.dto.UserAdminCreateRequestDto;
import com.example.demo.controller.admin.api.dto.UserAdminResponseDto;
import com.example.demo.controller.admin.api.dto.UserAdminUpdateRequestDto;
import com.example.demo.controller.internal.api.dto.RequestingUserDto;
import com.example.demo.repository.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserApiController
 *  - /src/main/java/com/example/demo/admin/api
 *    : 쿠팡 내부 MD 직원들이나 개발자 등이 상품이나 유저를 등록하고 삭제하기 위함 = 어드민 기능
 *      - 그 중에서 "User"ApiController 유저를 등록하고 삭제하기 위한 API
 */
@RestController
// 클래스에 @Controller 적고 + 각각의 메서드에 @ResponseBody 적어줬었는데 매번 메서드마다 해주기 번거로우니
// -> 클래스에 @RestController 적으면 = 각각의 메서드에 @ResponseBody 안적어줘도됨
@RequiredArgsConstructor
public class UserApiController {
    private final UserAdminApplication userAdminApplication;

    @RequestMapping(method = RequestMethod.GET, value = "/admin/api/users")
    public List<UserAdminResponseDto> retrieve() {
        return userAdminApplication.retrieve();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/api/users/{id}")
    public UserAdminResponseDto retrieve(@PathVariable Integer id) {
        return userAdminApplication.retrieve(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/api/users")
    public UserAdminResponseDto create(@RequestBody UserAdminCreateRequestDto request) {
        User creating = request.to();
        return userAdminApplication.create(creating);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/admin/api/users/{id}")
    public UserAdminResponseDto update(@PathVariable Integer id, @RequestBody UserAdminUpdateRequestDto request) {
        Integer requestedUserId = request.getRequestUserId();
        try (UserContext.ContextScope ignored = UserContext.withUser(requestedUserId)) {
            return userAdminApplication.update(id, request);
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/admin/api/users/{id}/active")
    public void active(@PathVariable Integer id, @RequestBody RequestingUserDto request) {
        Integer requestedUserId = request.getRequestUserId();
        try (UserContext.ContextScope ignored = UserContext.withUser(requestedUserId)) {
            userAdminApplication.active(id);
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/admin/api/users/{id}/soft-delete")
    public void softDelete(@PathVariable Integer id, @RequestBody RequestingUserDto request) {
        Integer requestedUserId = request.getRequestUserId();
        try (UserContext.ContextScope ignored = UserContext.withUser(requestedUserId)) {
            userAdminApplication.softDelete(id);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/admin/api/users/{id}/hard-delete")
    public void hardDelete(@PathVariable Integer id, @RequestBody RequestingUserDto request) {
        Integer requestedUserId = request.getRequestUserId();
        try (UserContext.ContextScope ignored = UserContext.withUser(requestedUserId)) {
            userAdminApplication.hardDelete(id);
        }
    }
}
