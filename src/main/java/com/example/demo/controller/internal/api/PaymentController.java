package com.example.demo.controller.internal.api;

import com.example.demo.repository.payment.Payment;
import com.example.demo.repository.payment.PaymentRepository;
import com.example.demo.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * PaymentController
 *  - /src/main/java/com/example/demo/internal/api
 *    : 리액트와 같은 CSR 즉, 실제 고객의 앱/웹 브라우저로부터 실제 고객의 구매와 구매취소 기능 / 버튼에 대한 API 제공
 *      1) 고객이 어떤 상품들을 구매할지 보내면 그 고객에게 해당 상품의 구매 정보를 생성
 *      2) 고객이 기존에 구매했던 구매건을 취소하는 경우 - Hard Delete 가 아닌 Soft Delete 상태변경으로
 */
@RestController
// 클래스에 @Controller 적고 + 각각의 메서드에 @ResponseBody 적어줬었는데 매번 메서드마다 해주기 번거로우니
// -> 클래스에 @RestController 적으면 = 각각의 메서드에 @ResponseBody 안적어줘도됨
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentRepository paymentRepository;
}
