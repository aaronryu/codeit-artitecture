package com.example.demo.repository.product;

import lombok.Getter;

@Getter
public class Product {
    private static int PRODUCT_CURRENT_ID = 0;
    private static int idGenerate() {
        return ++PRODUCT_CURRENT_ID;
    }

    private Integer id;
    private String name;
    private int price;
    private int stock;
    private boolean deleted = false;

    private Product(Integer id, String name, int price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
//      this.deleted = false;
    }

    public static Product create(String name, int price, int stock) {
        int generatedId = idGenerate();
        return new Product(generatedId, name, price, stock);
    }
}
