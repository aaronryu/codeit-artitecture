package com.example.demo.controller.api;

import com.example.demo.advice.dto.ApiResponse;
import com.example.demo.application.product.ProductAdminApplication;
import com.example.demo.common.context.UserContext;
import com.example.demo.controller.api.dto.ProductAdminResponseDto;
import com.example.demo.controller.api.dto.ProductAdminUpsertRequestDto;
import com.example.demo.controller.api.dto.RequestingUserDto;
import com.example.demo.exception.CodeitRuntimeException;
import com.example.demo.exception.ExceptionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/admin/api/products")
    public ApiResponse<List<ProductAdminResponseDto>> retrieve() {
//  public ResponseEntity<List<ProductAdminResponseDto>> retrieve() {
//      List<ProductAdminResponseDto> response = productAdminApplication.retrieve();
//      return ResponseEntity
//              .status(HttpStatus.OK)
//              .body(response);
        List<ProductAdminResponseDto> response = productAdminApplication.retrieve();
        return ApiResponse.success(response);
    }

    // 응답에 상태코드를 넣는 방법 2.
    // 2. 직접 ResponseEntity 반환 객체를 만들어서 반환
    @RequestMapping(method = RequestMethod.GET, value = "/admin/api/products/{id}")
    public ResponseEntity<ApiResponse<ProductAdminResponseDto>> retrieve(@PathVariable Integer id) {
        ProductAdminResponseDto response = productAdminApplication.retrieve(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(ApiResponse.success(response));
    }

    // 응답에 상태코드를 넣는 방법 1.
    // 1. 메서드 상단 어노테이션을 통해 명시 - 쉽지만 문제는 그 메서드에서 나가는 모든 응답에 그 상태코드가 들어감
    // = 익셉션에 따른 다른 상태코드를 반환하고싶을때 어쩔도리가 없음
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/admin/api/products")
    public ApiResponse<ProductAdminResponseDto> create(
            @RequestPart @Valid ProductAdminUpsertRequestDto request,
            @RequestPart(required = false) MultipartFile thumbnail
    ) {
//      ProductAdminResponseDto response = productAdminApplication.create(request, thumbnail);
//      return ResponseEntity
//              .status(HttpStatus.CREATED)
//              .body(response);
        ProductAdminResponseDto response = productAdminApplication.create(request, thumbnail);
        return ApiResponse.success(response);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.PUT, value = "/admin/api/products/{id}")
    public ApiResponse<ProductAdminResponseDto> update(
            @PathVariable @Min(1) Integer id,
            @RequestPart ProductAdminUpsertRequestDto request,
            @RequestPart(required = false) MultipartFile thumbnail
    ) {
        Integer requestedUserId = request.getRequestUserId();
        try (UserContext.ContextScope ignored = UserContext.withUser(requestedUserId)) {
            ProductAdminResponseDto response = productAdminApplication.update(id, request, thumbnail);
            return ApiResponse.success(response);
        }
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.PATCH, value = "/admin/api/products/{id}/active")
    public ApiResponse<Void> active(@PathVariable Integer id, @RequestBody RequestingUserDto request) {
        Integer requestedUserId = request.getRequestUserId();
        try (UserContext.ContextScope ignored = UserContext.withUser(requestedUserId)) {
            productAdminApplication.active(id);
            return ApiResponse.success();
        }
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.PATCH, value = "/admin/api/products/{id}/soft-delete")
    public ApiResponse<Void> softDelete(@PathVariable Integer id, @RequestBody RequestingUserDto request) {
        Integer requestedUserId = request.getRequestUserId();
        try (UserContext.ContextScope ignored = UserContext.withUser(requestedUserId)) {
            productAdminApplication.softDelete(id);
            return ApiResponse.success();
        }
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.DELETE, value = "/admin/api/products/{id}/hard-delete")
    public ApiResponse<Void> hardDelete(@PathVariable Integer id, @RequestBody RequestingUserDto request) {
        Integer requestedUserId = request.getRequestUserId();
        try (UserContext.ContextScope ignored = UserContext.withUser(requestedUserId)) {
            productAdminApplication.hardDelete(id);
            return ApiResponse.success();
        }
    }
}
