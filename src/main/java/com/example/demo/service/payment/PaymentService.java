package com.example.demo.service.payment;

import com.example.demo.repository.payment.Payment;
import com.example.demo.repository.payment.PaymentRepository;
import com.example.demo.repository.payment.PaymentStatus;
import com.example.demo.repository.product.Product;
import com.example.demo.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * PaymentService
 *  - Service 명칭 자체가 기본적으로 도메인 서비스를 의미 Domain Service
 *      = Domain Service <- Domain Repository
 *  - Payment 도메인(엔티티 객체)을 파라미터로 받거나 반환값으로 반환하는
 *      = PaymentService 와 외부 Application 의 관계는
 *          - 외부 Application 에게 Payment 반환해주거나
 *          - 외부 Application 로부터 Payment 받아서 그것에 대한 처리를 해주거나
 *              * 처리 : CRUD 에 국한된다 / 주의 ! 도메인 내부 상태를 바꾸는 메서드는 Application 에서 호출할것 !
 *  - Domain Service 서비스의 강제사항은 단 하나의 Domain Repository 만 필드로 가져야한다는것
 */
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public Payment getPayment(Integer id) {
        Optional<Payment> wrappedPayment = paymentRepository.findById(id);
                 Payment         payment = wrappedPayment
                .orElseThrow(() -> new RuntimeException("찾으시는 결제가 존재하지 않습니다"));
        return payment;
    }

    public Payment create(Payment entity) {
        Optional<Payment> wrappedCreated = paymentRepository.create(entity);
                 Payment         created = wrappedCreated
                         .orElseThrow(() -> new RuntimeException("결제가 정상적으로 생성되지 않습니다"));
        return created;
    }

    public Payment update(Payment entity) {
        Optional<Payment> wrappedPayment = paymentRepository.update(entity);
                 Payment         payment = wrappedPayment
                .orElseThrow(() -> new RuntimeException("업데이트가 정상적으로 되지 않습니다"));
        return payment;
    }
}
