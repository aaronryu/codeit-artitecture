package com.example.demo.controller.internal.api;

import com.example.demo.controller.internal.api.dto.PaymentCreateRequestDto;
import com.example.demo.controller.internal.api.dto.PaymentResponseDto;
import com.example.demo.controller.internal.api.dto.RequestingUserDto;
import com.example.demo.repository.payment.Payment;
import com.example.demo.repository.payment.PaymentRepository;
import com.example.demo.repository.payment.PaymentStatus;
import com.example.demo.repository.product.Product;
import com.example.demo.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private final ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/internal/api/payments")
    public PaymentResponseDto payment(@RequestBody PaymentCreateRequestDto request) {
        Integer requestedUserId = request.getRequestUserId();
        PaymentResponseDto.PaymentResponseDtoBuilder responseBuilder = PaymentResponseDto.builder();
        // 1. 구매하려는 상품이 존재하는지 + 상품의 재고가 충분한지 검증
        List<Product> products = new ArrayList<>();
        List<Integer> productIds = request.getProductIds();
        for (Integer productId : productIds) {
            Optional<Product> wrappedProduct = productRepository.findById(productId);
                     Product         product = wrappedProduct
                             .orElseThrow(() -> new RuntimeException("찾으시는 상품이 존재하지 않습니다 - id : " + productId));
            if (product.getStock() < 1) {
                throw new RuntimeException("구매하시려는 상품의 재고가 존재하지 않습니다 - product: " + product);
            }
            products.add(product);
        }
        // 2. 실제 구매 완료
        Payment creating = Payment.create(products, requestedUserId);
        creating.setStatus(PaymentStatus.PAYMENT_COMPLETE);
        creating.setPurchasedAt(LocalDateTime.now());
        creating.updated(requestedUserId);
        Optional<Payment> wrappedCreated = paymentRepository.create(creating);
                 Payment         created = wrappedCreated
                         .orElseThrow(() -> new RuntimeException("결제가 정상적으로 생성되지 않습니다"));
        responseBuilder.payment(created);
        // 3. 구매가 완료된 상품들에 대해서 재고 1개씩 차감
        for (Product product : products) {
            product.setStock(product.getStock() - 1);
            productRepository.update(product);
        }
        responseBuilder.products(products);
        return responseBuilder.build();
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/internal/api/payments/{id}/cancel")
    public PaymentResponseDto cancel(@PathVariable Integer id, @RequestBody RequestingUserDto request) {
        Integer requestedUserId = request.getRequestUserId();
        PaymentResponseDto.PaymentResponseDtoBuilder responseBuilder = PaymentResponseDto.builder();
        // 1. 취소하려는 결제건이 존재하는지 확인
        Optional<Payment> wrappedPayment = paymentRepository.findById(id);
                 Payment         payment = wrappedPayment
                .orElseThrow(() -> new RuntimeException("취소하려는 결제가 존재하지 않습니다 - id : " + id));
        // 2. 취소 완료
        PaymentStatus currentStatus = payment.getStatus();
        if (!currentStatus.isCancellable()) {
            throw new RuntimeException("취소하시려는 결제는 취소할 수 없는 상태입니다 - id : " + payment.getId() + ", status : " + currentStatus);
        }
        payment.setStatus(PaymentStatus.CANCEL_COMPLETE);
        payment.setCancelledAt(LocalDateTime.now());
        payment.updated(requestedUserId);
        responseBuilder.payment(payment);
        // 3. 취소한 결제건에 들어있던 모든 상품들의 재고를 1 증가시키며 롤백
        List<Product> products = new ArrayList<>();
        List<Integer> productIds = payment.getProductIds();
        for (Integer productId : productIds) {
            Optional<Product> wrappedProduct = productRepository.findById(productId);
            Product         product = wrappedProduct
                    .orElseThrow(() -> new RuntimeException("결제한 상품이 존재하지 않습니다 - id : " + productId));
            product.setStock(product.getStock() + 1);
            productRepository.update(product);
            products.add(product);
        }
        responseBuilder.products(products);
        return responseBuilder.build();
    }
}
