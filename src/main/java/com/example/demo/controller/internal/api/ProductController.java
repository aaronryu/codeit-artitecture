package com.example.demo.controller.internal.api;

import com.example.demo.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductController
 *  - /src/main/java/com/example/demo/internal/api
 *    : 리액트와 같은 CSR 즉, 실제 고객의 앱/웹 브라우저로부터 실제 고객의 구매와 구매취소 기능 / 버튼에 대한 API 제공
 *      1) 고객이 쿠팡에 어떤 물건들이 있지? 하고 확인할 수 있는 전체 상품 조회
 *      2) 고객이 특정 상품에 대한 상세 정보를 볼 수 있게 할 단일 상품 조회
 */
@RestController
// 클래스에 @Controller 적고 + 각각의 메서드에 @ResponseBody 적어줬었는데 매번 메서드마다 해주기 번거로우니
// -> 클래스에 @RestController 적으면 = 각각의 메서드에 @ResponseBody 안적어줘도됨
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
}
