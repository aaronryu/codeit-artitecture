package com.example.demo.repository.payment;

import com.example.demo.common.context.UserContext;
import com.example.demo.repository.BaseEntity;
import com.example.demo.repository.product.Product;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ToString(callSuper = true)
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

    public static Payment create(List<Product> products) {
        int generatedId = idGenerate();
        Integer currentUserId = UserContext.getUserId(); /* 누가 구매를 하였는지 */
        List<Integer> productIds = products.stream()
                .map(Product::getId)
                .toList();
        int paidPrice = products.stream()
                .map(Product::getPrice)
                .reduce(0, Integer::sum);
        return new Payment(generatedId, productIds, paidPrice, currentUserId);
    }

    public void complete() {
        // ThreadLocal 로부터 현재 요청한 유저 ID 를 꺼내와서 권한 검증
        Integer currentUserId = UserContext.getUserId();
        if (!currentUserId.equals(super.createdBy)) {
            throw new RuntimeException("취소하려는 유져와 취소하려는 결제를 수행한 유저가 다릅니다 - requestedUserId : " + currentUserId + " != paymentUserId: " + super.createdBy);
        }
        if (this.status.compareTo(PaymentStatus.PAYMENT_COMPLETE) > 0) {
            throw new RuntimeException("결제 완료로 상태를 바꿀 수 없는 결제건입니다 - payment : " + this.toString());
        }
        this.status = PaymentStatus.PAYMENT_COMPLETE;
        this.purchasedAt = LocalDateTime.now();
        super.updated();
    }

    public void delivering() {
        if (this.status.compareTo(PaymentStatus.IN_DELIVERY) > 0) {
            throw new RuntimeException("배송 중으로 상태를 바꿀 수 없는 결제건입니다 - payment : " + this.toString());
        }
        this.status = PaymentStatus.IN_DELIVERY;
        super.updated();
    }

    public void delivered() {
        if (this.status.compareTo(PaymentStatus.DELIVERY_COMPLETE) > 0) {
            throw new RuntimeException("배송 완료로 상태를 바꿀 수 없는 결제건입니다 - payment : " + this.toString());
        }
        this.status = PaymentStatus.DELIVERY_COMPLETE;
        this.deliveredAt = LocalDateTime.now();
        super.updated();
    }

    public void cancel() {
        // ThreadLocal 로부터 현재 요청한 유저 ID 를 꺼내와서 권한 검증
        Integer currentUserId = UserContext.getUserId();
        if (!currentUserId.equals(super.createdBy)) {
            throw new RuntimeException("취소하려는 유져와 취소하려는 결제를 수행한 유저가 다릅니다 - requestedUserId : " + currentUserId + " != paymentUserId: " + super.createdBy);
        }
        if (!this.status.isCancellable()) {
            throw new RuntimeException("취소하시려는 결제는 취소할 수 없는 상태입니다 - id : " + this.id + ", status : " + this.status);
        }
        this.status = PaymentStatus.CANCEL_COMPLETE;
        this.cancelledAt = LocalDateTime.now();
        super.updated();
    }
}
