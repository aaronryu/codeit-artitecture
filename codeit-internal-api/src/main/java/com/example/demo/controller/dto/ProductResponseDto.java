package com.example.demo.controller.dto;

import com.example.demo.repository.product.Product;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductResponseDto {
    private final Integer id;
    private final String name;
    private final int price;
    private final int stock;

    public static ProductResponseDto from(Product entity) {
        return new ProductResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getStock()
        );
    }
}
