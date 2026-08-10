package com.example.demo.repository.product;

import com.example.demo.repository.BaseEntity;
import lombok.Getter;

@Getter
public class Product extends BaseEntity {
    private static int PRODUCT_CURRENT_ID = 0;
    private static int idGenerate() {
        return ++PRODUCT_CURRENT_ID;
    }

    private String name;
    private int price;
    private int stock;

    private Product(Integer id, String name, int price, int stock, Integer userId) {
        super(id, userId);
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public static Product create(String name, int price, int stock, /* 누가 상품을 생성했는지 */  Integer userId) {
        int generatedId = idGenerate();
        return new Product(generatedId, name, price, stock, userId);
    }
}
