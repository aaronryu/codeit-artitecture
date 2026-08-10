package com.example.demo.controller.admin.api;

import com.example.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

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
    private final UserRepository userRepository;
}
