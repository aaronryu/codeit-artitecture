package com.example.demo.controller.api;

import com.example.demo.application.product.ProductAdminApplication;
import com.example.demo.common.context.UserContext;
import com.example.demo.controller.api.dto.ProductAdminResponseDto;
import com.example.demo.controller.api.dto.ProductAdminUpsertRequestDto;
import com.example.demo.controller.api.dto.RequestingUserDto;
import com.example.demo.repository.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProductApiController
 *  - /src/main/java/com/example/demo/admin/api
 *    : 쿠팡 내부 MD 직원들이나 개발자 등이 상품이나 유저를 등록하고 삭제하기 위함 = 어드민 기능
 *      - 그 중에서 "Product"ApiController 상품을 등록하고 삭제하기 위한 API
 */
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

    @RequestMapping(method = RequestMethod.GET, value = "/admin/api/products/{id}")
    public ProductAdminResponseDto retrieve(@PathVariable Integer id) {
        return productAdminApplication.retrieve(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/api/products")
    public ProductAdminResponseDto create(@RequestBody ProductAdminUpsertRequestDto request) {
        Product creating = request.to();
        return productAdminApplication.create(creating);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/admin/api/products/{id}")
    public ProductAdminResponseDto update(@PathVariable Integer id, @RequestBody ProductAdminUpsertRequestDto request) {
        Integer requestedUserId = request.getRequestUserId();
        try (UserContext.ContextScope ignored = UserContext.withUser(requestedUserId)) {
            return productAdminApplication.update(id, request);
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
