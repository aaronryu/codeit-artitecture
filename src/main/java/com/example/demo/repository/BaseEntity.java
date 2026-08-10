package com.example.demo.repository;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * BaseEntity
 * : 세상에 존재하는 모든 엔티티들은 공통적으로 아래의 필드를 갖는다
 *  - id
 *  - deleted
 *  - createdAt : 언제 '생성'되었는가?
 *  - createdBy : 누가 '생성'하였는가?
 *  - updatedAt : 언제 '갱신'되었는가?
 *  - updatedBy : 누가 '갱신'하였는가?
 */
@ToString
@Getter
// public class BaseEntity { -> BaseEntity 는 개별적인 객체로 생성(new BaseEntity(...))되어서는 안됨! 단순히 템플릿 클래스로의 역할만 수행하도록
public abstract class BaseEntity {
//  private static int GLOBAL_CURRENT_ID = 0; - 만약 이렇게 하게되면 모든 엔티티 객체들이 하나의 아이디 채번을 사용하게되는 문제
//  - new Payment -> ID 1 발급
//  - new Product -> ID 2 발급
//  - new Payment -> ID 3 발급
//  - new User    -> ID 4 발급
    private Integer id;
    private boolean deleted = false;
    // Audit 필드 : 데이터의 생성과 수정이 누구로 인해 언제 이뤄졌는지를 기록 - 누가 / 언제를 추적할 수 있도록
    private LocalDateTime createdAt; // 해당 데이터가 언제 '추가'되었고
    private       Integer createdBy; // 해당 데이터가 누가 '추가'하였고
    private LocalDateTime updatedAt; // 해당 데이터가 언제 '수정'되었고
    private       Integer updatedBy; // 해당 데이터가 누가 '수정'했는지

    protected BaseEntity(Integer id, Integer userId) {
        this.id        = id;
//      this.deleted   = false;
        this.createdAt = LocalDateTime.now();
        this.createdBy = userId;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = userId;
    }

    /**
     * BaseEntity (템플릿) 추상클래스 상속받는 엔티티 내 필드들이 수정되었을때 누가, 언제 바꿨는지 기록
     *  - 중요 ! 이번 예시에서는 필드가 갱신되는 엔티티는 Payment 하나에서만 발생하는것으로 진행할 것 !
     * @param userId - 어떤 유저가 값을 바꿨는지 추적하기 위함 <- Auditing
     */
    public void updated(Integer userId) {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = userId;
    }
}
