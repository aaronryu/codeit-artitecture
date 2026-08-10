package com.example.demo.repository.product;

import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * ProductRepository
 * : Repository 의미 자체가 저장소이니 Product 정보에 대한 CRUD (생성, 조회, 갱신, 삭제) 제공
 *  - CRUD 중 U 제외 - 생성과 삭제만 있다고 가정
 *      - R (2가지) : 전체 조회 / 단일 조회
 *      - C : 단일 생성
 *      - D : 단일 삭제
 */
@Repository
public class ProductRepository {
    private final static Map<Integer, Product> PRODUCTS = new HashMap<>();

    // R 전체 조회
    public List<Product> findAll() {
        return PRODUCTS.values().stream().toList();
    }

    // R 단일 조회
    public Optional<Product> findById(Integer id) {
        return Optional.ofNullable(PRODUCTS.get(id));
    }

    // C 단일 생성
    public Optional<Product> create(Product entity) {
        int id = entity.getId();
        if (Objects.nonNull(PRODUCTS.get(id))) {
            throw new RuntimeException("기존에 해당하는 아이디를 가진 상품이 이미 존재합니다 - id : " + id);
        }
        Product created = PRODUCTS.put(id, entity);
        return Optional.ofNullable(created);
    }

    // D 단일 삭제
    public void remove(Integer id) {
        if (Objects.isNull(PRODUCTS.get(id))) {
            throw new RuntimeException("기존에 해당 아이디를 가진 상품이 존재하지 않습니다 - id : " + id);
        }
        PRODUCTS.remove(id);
    }
}
