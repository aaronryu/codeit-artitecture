package com.example.demo.repository.payment;

import com.example.demo.repository.BaseEntity;
import com.example.demo.repository.product.Product;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Payment extends BaseEntity {
    private static int PAYMENT_CURRENT_ID = 0;
    private static int idGenerate() {
        return ++PAYMENT_CURRENT_ID;
    }

    private List<Integer> productIds;
    private PaymentStatus status = PaymentStatus.IN_PAYMENT;
    private int paidPrice;
    private LocalDateTime purchasedAt; // 결제 완료 시점
    private LocalDateTime deliveredAt; // 배송 완료 시점
    private LocalDateTime cancelledAt; // 취소 완료 시점

    private Payment(Integer id, List<Integer> productIds, int paidPrice, Integer userId) {
        super(id, userId);
        this.productIds = productIds;
        this.paidPrice = paidPrice;
    }

    public static Payment create(List<Product> products, /* 누가 구매를 하였는지 */ Integer userId) {
        int generatedId = idGenerate();
        List<Integer> productIds = products.stream()
                .map(Product::getId)
                .toList();
        int paidPrice = products.stream()
                .map(Product::getPrice)
                .reduce(0, Integer::sum);
        return new Payment(generatedId, productIds, paidPrice, userId);
    }
}
