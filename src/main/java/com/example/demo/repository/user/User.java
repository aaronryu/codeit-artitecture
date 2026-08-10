package com.example.demo.repository.user;

import com.example.demo.repository.BaseEntity;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
public class User extends BaseEntity {
    private static int USER_CURRENT_ID = 0;
    private static int idGenerate() {
        return ++USER_CURRENT_ID;
    }

    private String name;

    private User(Integer id, String name, Integer userId) {
        super(id, userId);
        this.name = name;
    }

    public static User create(String name, /* 누가 유저를 생성했는지 */ Integer userId) {
        int generatedId = idGenerate();
        return new User(generatedId, name, userId);
    }
}
