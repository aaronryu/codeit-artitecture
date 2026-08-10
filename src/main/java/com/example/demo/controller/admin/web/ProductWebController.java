package com.example.demo.controller.admin.web;

import com.example.demo.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * ProductWebController
 *  - /src/main/java/com/example/demo/admin/web
 *    : 쿠팡 내부 MD 직원들이나 개발자 등이 상품이나 유저를 등록하고 삭제하기 위함 = 어드민 기능
 *      - 그 중에서 "Product"WebController 상품을 등록하고 삭제하기 위한 HTML 페이지 (SSR)
 *                          스프링 서버에서 Thymeleaf 통해 페이지를 만들어 반환한다는 뜻 =
 */
@Controller
// 페이지만을 제공할것이라서 각 메서드마다 @ResponseBody 가 필요없음 - JSON 반환이 아니라 HTML 반환임
@RequiredArgsConstructor
public class ProductWebController {
    private final ProductRepository productRepository;
}
