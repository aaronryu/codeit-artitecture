package com.example.demo.repository.user;

import com.example.demo.repository.IRepository;
import com.example.demo.repository.payment.Payment;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * UserRepository
 * : Repository 의미 자체가 저장소이니 User 정보에 대한 CRUD (생성, 조회, 갱신, 삭제) 제공
 *  - CRUD 중 U 제외 - 생성과 삭제만 있다고 가정
 *      - R (2가지) : 전체 조회 / 단일 조회
 *      - C : 단일 생성
 *      - D : 단일 삭제
 */
@Repository
public class UserRepository implements IRepository<Integer, User> {
    private final static Map<Integer, User> USERS = new HashMap<>();

    @Override
    // R 전체 조회
    public List<User> findAll() {
        return USERS.values().stream().toList();
    }

    @Override
    // R 단일 조회
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(USERS.get(id));
    }

    @Override
    // C 단일 생성
    public Optional<User> create(User entity) {
        int id = entity.getId();
        if (Objects.nonNull(USERS.get(id))) {
            throw new RuntimeException("기존에 해당하는 아이디를 가진 유저가 이미 존재합니다 - id : " + id);
        }
        User created = USERS.put(id, entity);
        return Optional.ofNullable(created);
    }

    @Override
    // U 단일 갱신
    public Optional<User> update(User entity) {
        int id = entity.getId();
        if (Objects.isNull(USERS.get(id))) {
            throw new RuntimeException("기존에 해당 아이디를 가진 유저가 존재하지 않습니다 - id : " + id);
        }
        User updated = USERS.replace(id, entity);
        return Optional.ofNullable(updated);
    }

    @Override
    // D 단일 삭제
    public void delete(Integer id) {
        if (Objects.isNull(USERS.get(id))) {
            throw new RuntimeException("기존에 해당 아이디를 가진 유저가 존재하지 않습니다 - id : " + id);
        }
        USERS.remove(id);
    }
}
