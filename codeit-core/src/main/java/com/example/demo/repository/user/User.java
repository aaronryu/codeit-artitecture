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
    private UserGrade grade = UserGrade.BRONZE;
    private int point = 0;
    private String thumbnail;

    private User(Integer id, String name, Integer userId) {
        super(id, userId);
        this.name = name;
    }

    public static User create(String name, /* 누가 유저를 생성했는지 */ Integer userId) {
        int generatedId = idGenerate();
        return new User(generatedId, name, userId);
    }

    public void upload(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void update(String name, UserGrade grade, int point) {
        this.name = name;
        this.grade = grade;
        this.point = point;
        super.updated();
    }

    public void earn(int paidPrice) {
        this.point += (int) (paidPrice * this.grade.getEarningRate());
    }
}
