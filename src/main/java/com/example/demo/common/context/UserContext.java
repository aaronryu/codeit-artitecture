package com.example.demo.common.context;

public class UserContext {
    private static final ThreadLocal<Integer> CURRENT_USER = new ThreadLocal<>();

    public static void setUserId(Integer userId) {
        CURRENT_USER.set(userId);
    }

    public static Integer getUserId() {
        return CURRENT_USER.get();
    }

    public static void clear() {
        CURRENT_USER.remove();
    }

//  - Try-with-resources 구문에서 사용할 AutoCloseable 스코프 생성
//  > (방법 1) 함수형 인터페이스를 제대로 활용한 경우
//  public static AutoCloseable withUser(Integer userId) {
//      setUserId(userId);
//      return UserContext::clear;
//  }

//  - Try-with-resources 구문에서 사용할 AutoCloseable 스코프 생성
//  > (방법 2) 구식의 방법이지만 이해하기는 쉬운 방법
    public static ContextScope withUser(Integer userId) {
        setUserId(userId);
        return new ContextScope();
    }

//  - close() 실행 시 자동으로 clear()를 호출해주는 내부 클래스
    public static class ContextScope implements AutoCloseable {
        @Override
        public void close() {
            clear(); // try 블록이 끝나면 자동으로 실행
        }
    }
}
