package com.example.demo.controller.internal.api.dto;

import com.example.demo.repository.payment.Payment;
import com.example.demo.repository.payment.PaymentStatus;
import com.example.demo.repository.product.Product;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class PaymentResponseDto {
    private Integer id;
    private List<ProductResponseDto> products;
    private PaymentStatus status = PaymentStatus.IN_PAYMENT;
    private int paidPrice;
    private LocalDateTime purchasedAt; // 결제 완료 시점
    private LocalDateTime deliveredAt; // 배송 완료 시점
    private LocalDateTime cancelledAt; // 취소 완료 시점

    @Builder
    private PaymentResponseDto(Payment payment, List<Product> products) {
        this.id = payment.getId();

    }
}
