package com.example.demo.controller.api;

import com.example.demo.application.product.ProductAdminApplication;
import com.example.demo.common.context.UserContext;
import com.example.demo.controller.api.dto.ProductAdminResponseDto;
import com.example.demo.controller.api.dto.ProductAdminUpsertRequestDto;
import com.example.demo.controller.api.dto.RequestingUserDto;
import com.example.demo.exception.CodeitRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ProductApiController
 *  - /src/main/java/com/example/demo/admin/api
 *    : 쿠팡 내부 MD 직원들이나 개발자 등이 상품이나 유저를 등록하고 삭제하기 위함 = 어드민 기능
 *      - 그 중에서 "Product"ApiController 상품을 등록하고 삭제하기 위한 API
 */
@Slf4j
@RestController
// 클래스에 @Controller 적고 + 각각의 메서드에 @ResponseBody 적어줬었는데 매번 메서드마다 해주기 번거로우니
// -> 클래스에 @RestController 적으면 = 각각의 메서드에 @ResponseBody 안적어줘도됨
@RequiredArgsConstructor
public class ProductApiController {
    private final ProductAdminApplication productAdminApplication;

    @RequestMapping(method = RequestMethod.GET, value = "/admin/api/products")
    public List<ProductAdminResponseDto> retrieve() {
        return productAdminApplication.retrieve();
    }

    // 응답에 상태코드를 넣는 방법 2.
    // 2. 직접 ResponseEntity 반환 객체를 만들어서 반환
    @RequestMapping(method = RequestMethod.GET, value = "/admin/api/products/{id}")
    public ResponseEntity<ProductAdminResponseDto> retrieve(@PathVariable Integer id) {
        try {
            ProductAdminResponseDto response = productAdminApplication.retrieve(id);
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(response);
        } catch (CodeitRuntimeException e) {
            // 내가 알고있거나 / 명시적으로 처리하고싶어하는 예외 상황에 대해 이렇게 구체적인 예외 클래스를 명시해서 처리
            return switch (e.getType()) {
                case DATABASE_CONNECTION_FAILED -> {
                    log.error(e.getMessage(), e);
                    yield ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .build();
                }
                case USER_NOT_FOUND -> {
                    log.warn(e.getMessage(), e);
                    yield ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .build();
                }
            };
        } catch (RuntimeException e /* 클래스 다형성에 의해 우리가 만드는 예외 Exception 들이 모두 RuntimeException 상속받기에 여기로 다 들어옴 */) {
            // 세상에는 (라이브러리, 프레임워크 등) 너무 다양한 예외들이 존재하기에 우리가 catch 하지 못하고 놓친 예외에 대해 꼭 마지막까지 처리해줘야한다
            // = switch 구문에서 default 와 거의 같은 목적의 코드라고 보면 된다
            log.error("우리가 커버하지 못한 예외 발생", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // 응답에 상태코드를 넣는 방법 1.
    // 1. 메서드 상단 어노테이션을 통해 명시 - 쉽지만 문제는 그 메서드에서 나가는 모든 응답에 그 상태코드가 들어감
    // = 익셉션에 따른 다른 상태코드를 반환하고싶을때 어쩔도리가 없음
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/admin/api/products")
    public ProductAdminResponseDto create(
            @RequestPart ProductAdminUpsertRequestDto request,
            @RequestPart(required = false) MultipartFile thumbnail
    ) {
        return productAdminApplication.create(request, thumbnail);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/admin/api/products/{id}")
    public ProductAdminResponseDto update(
            @PathVariable Integer id,
            @RequestPart ProductAdminUpsertRequestDto request,
            @RequestPart(required = false) MultipartFile thumbnail
    ) {
        Integer requestedUserId = request.getRequestUserId();
        try (UserContext.ContextScope ignored = UserContext.withUser(requestedUserId)) {
            return productAdminApplication.update(id, request, thumbnail);
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/admin/api/products/{id}/active")
    public void active(@PathVariable Integer id, @RequestBody RequestingUserDto request) {
        Integer requestedUserId = request.getRequestUserId();
        try (UserContext.ContextScope ignored = UserContext.withUser(requestedUserId)) {
            productAdminApplication.active(id);
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/admin/api/products/{id}/soft-delete")
    public void softDelete(@PathVariable Integer id, @RequestBody RequestingUserDto request) {
        Integer requestedUserId = request.getRequestUserId();
        try (UserContext.ContextScope ignored = UserContext.withUser(requestedUserId)) {
            productAdminApplication.softDelete(id);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/admin/api/products/{id}/hard-delete")
    public void hardDelete(@PathVariable Integer id, @RequestBody RequestingUserDto request) {
        Integer requestedUserId = request.getRequestUserId();
        try (UserContext.ContextScope ignored = UserContext.withUser(requestedUserId)) {
            productAdminApplication.hardDelete(id);
        }
    }
}
