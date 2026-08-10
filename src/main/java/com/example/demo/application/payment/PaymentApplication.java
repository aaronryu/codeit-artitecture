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
public class PaymentApplication implements IPaymentApplication {
    private final PaymentService paymentService;
    private final ProductService productService;

    public PaymentResponseDto payment(List<Integer> productIds) {
        // 1. 구매하려는 상품이 존재하는지 + 상품의 재고가 충분한지 검증 + 재고 1개씩 차감
        List<Product> updatedProducts = productIds.stream()
                .map((productId) -> {
                    Product product = productService.getProduct(productId);
                    product.buyable();
                    product.decrease();
                    return product;
                })
                .toList();
        productService.update(updatedProducts);
        // 2. 실제 구매 완료
        Payment creating = Payment.create(updatedProducts);
        creating.complete();
        Payment createdPayment = paymentService.create(creating);
        return PaymentResponseDto.builder()
                .payment(createdPayment)
                .products(updatedProducts)
                .build();
    }

    public PaymentResponseDto cancel(Integer id) {
        // 1. 취소하려는 결제건이 존재하는지 확인
        Payment cancelledPayment = paymentService.getPayment(id);
        // 2. 취소 완료
        cancelledPayment.cancel();
        paymentService.update(cancelledPayment);
        // 3. 취소한 결제건에 들어있던 모든 상품들의 재고를 1 증가시키며 롤백
        List<Product> rollbackProducts = cancelledPayment.getProductIds().stream()
                .map((productId) -> {
                    Product product = productService.getProduct(productId);
                    product.increase();
                    return product;
                })
                .toList();
        productService.update(rollbackProducts);
        return PaymentResponseDto.builder()
                .payment(cancelledPayment)
                .products(rollbackProducts)
                .build();
    }
}
