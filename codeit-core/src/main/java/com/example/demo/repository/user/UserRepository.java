package com.example.demo.repository.user;

import com.example.demo.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 * : Repository 의미 자체가 저장소이니 User 정보에 대한 CRUD (생성, 조회, 갱신, 삭제) 제공
 *  - CRUD 중 U 제외 - 생성과 삭제만 있다고 가정
 *      - R (2가지) : 전체 조회 / 단일 조회
 *      - C : 단일 생성
 *      - D : 단일 삭제
 */
@Repository
public class UserRepository extends AbstractRepository<User> {
}
