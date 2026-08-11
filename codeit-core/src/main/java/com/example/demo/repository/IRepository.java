package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

public interface IRepository<ID, ENTITY> {
    // R 전체 조회
    List<ENTITY> findAll();

    // R 단일 조회
    Optional<ENTITY> findById(ID id);

    // C 단일 생성
    Optional<ENTITY> create(ENTITY entity);

    // U 단일 수정
    Optional<ENTITY> update(ENTITY entity);

    // D 단일 삭제
    void delete(ID id);
}
