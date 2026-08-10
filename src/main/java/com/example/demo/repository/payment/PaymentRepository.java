package com.example.demo.repository.payment;

import com.example.demo.repository.IRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * PaymentRepository
 * : Repository 의미 자체가 저장소이니 Payment 정보에 대한 CRUD (생성, 조회, 갱신, 삭제) 제공
 *  - CRUD 모두 제공 - 결제나 배송 상태를 지속적으로 바꿔줘야하기 때문에 U 갱신 필요
 *      - R (2가지) : 전체 조회 / 단일 조회
 *      - C : 단일 생성
 *      - U : 단일 갱신
 *      - D : 단일 삭제
 */
@Repository
public class PaymentRepository implements IRepository<Integer, Payment> {
    private final static Map<Integer, Payment> PAYMENTS = new HashMap<>();

    @Override
    // R 전체 조회
    public List<Payment> findAll() {
        return PAYMENTS.values().stream().toList();
    }

    @Override
    // R 단일 조회
    public Optional<Payment> findById(Integer id) {
        return Optional.ofNullable(PAYMENTS.get(id));
    }

    @Override
    // C 단일 생성
    public Optional<Payment> create(Payment entity) {
        int id = entity.getId();
        if (Objects.nonNull(PAYMENTS.get(id))) {
            throw new RuntimeException("기존에 해당하는 아이디를 가진 결제가 이미 존재합니다 - id : " + id);
        }
        Payment created = PAYMENTS.put(id, entity);
        return Optional.ofNullable(created);
    }

    @Override
    // U 단일 갱신
    public Optional<Payment> update(Payment entity) {
        int id = entity.getId();
        if (Objects.isNull(PAYMENTS.get(id))) {
            throw new RuntimeException("기존에 해당 아이디를 가진 결제가 존재하지 않습니다 - id : " + id);
        }
        Payment updated = PAYMENTS.replace(id, entity);
        return Optional.ofNullable(updated);
    }

    @Override
    // D 단일 삭제
    public void delete(Integer id) {
        if (Objects.isNull(PAYMENTS.get(id))) {
            throw new RuntimeException("기존에 해당 아이디를 가진 결제가 존재하지 않습니다 - id : " + id);
        }
        PAYMENTS.remove(id);
    }
}
