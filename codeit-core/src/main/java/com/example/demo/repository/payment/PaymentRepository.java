package com.example.demo.repository.payment;

import com.example.demo.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

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
public class PaymentRepository extends AbstractRepository<Payment> {
}
