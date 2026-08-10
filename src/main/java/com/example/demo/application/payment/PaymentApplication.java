package com.example.demo.application.payment;

import com.example.demo.controller.internal.api.dto.PaymentResponseDto;
import com.example.demo.repository.payment.Payment;
import com.example.demo.repository.payment.PaymentRepository;
import com.example.demo.repository.payment.PaymentStatus;
import com.example.demo.repository.product.Product;
import com.example.demo.repository.product.ProductRepository;
import com.example.demo.service.payment.PaymentService;
import com.example.demo.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentApplication {
    private final PaymentService paymentService;
    private final ProductService productService;

    public PaymentResponseDto payment(List<Integer> productIds, Integer requestedUserId) {
        PaymentResponseDto.PaymentResponseDtoBuilder responseBuilder = PaymentResponseDto.builder();
        // 1. 구매하려는 상품이 존재하는지 + 상품의 재고가 충분한지 검증
        List<Product> products = new ArrayList<>();
        for (Integer productId : productIds) {
            Product product = productService.getProduct(productId);
            product.buyable();
            products.add(product);
        }
        // 2. 실제 구매 완료
        Payment creating = Payment.create(products, requestedUserId);
        creating.complete(requestedUserId);
        Payment created = paymentService.create(creating);
        responseBuilder.payment(created);
        // 3. 구매가 완료된 상품들에 대해서 재고 1개씩 차감
        for (Product product : products) {
            product.decrease();
            productService.update(product);
        }
        responseBuilder.products(products);
        return responseBuilder.build();
    }

    public PaymentResponseDto cancel(Integer id, Integer requestedUserId) {
        PaymentResponseDto.PaymentResponseDtoBuilder responseBuilder = PaymentResponseDto.builder();
        // 1. 취소하려는 결제건이 존재하는지 확인
        Payment payment = paymentService.getPayment(id);
        // 2. 취소 완료
        payment.cancel(requestedUserId);
        paymentService.update(payment);
        responseBuilder.payment(payment);
        // 3. 취소한 결제건에 들어있던 모든 상품들의 재고를 1 증가시키며 롤백
        List<Product> products = new ArrayList<>();
        List<Integer> productIds = payment.getProductIds();
        for (Integer productId : productIds) {
            Product product = productService.getProduct(productId);
            product.increase();
            productService.update(product);
            products.add(product);
        }
        responseBuilder.products(products);
        return responseBuilder.build();
    }
}
