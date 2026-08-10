package com.example.demo.repository.user;

import lombok.Getter;

@Getter
public class User {
    private static int USER_CURRENT_ID = 0;
    private static int idGenerate() {
        return ++USER_CURRENT_ID;
    }

    private Integer id;
    private String name;
    private boolean deleted = false;

    private User(Integer id, String name) {
        this.id = id;
        this.name = name;
//      this.deleted = false;
    }

    public static User create(String name) {
        int generatedId = idGenerate();
        return new User(generatedId, name);
    }
}
