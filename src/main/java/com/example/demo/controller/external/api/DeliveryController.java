package com.example.demo.controller.external.api;

import com.example.demo.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * DeliveryController
 *  - /src/main/java/com/example/demo/external/api
 *    : 로젠택배와 같은 배송대행업체에서 쿠팡에서 고객이 구매한 상품의 배송을
 *      시작했는지 / 잘마쳤는지 여부를 쿠팡인 우리에게 알려주기 위해 호출하는 API
 *      1) 고객이 구매한 구매건의 상품에 대한 배송을 시작했을때
 *      2) 고객이 구매한 구매건의 상품에 대한 배송이 완료됐을때
 */
@RestController
// 클래스에 @Controller 적고 + 각각의 메서드에 @ResponseBody 적어줬었는데 매번 메서드마다 해주기 번거로우니
// -> 클래스에 @RestController 적으면 = 각각의 메서드에 @ResponseBody 안적어줘도됨
@RequiredArgsConstructor
public class DeliveryController {
    private final PaymentRepository paymentRepository;
}
