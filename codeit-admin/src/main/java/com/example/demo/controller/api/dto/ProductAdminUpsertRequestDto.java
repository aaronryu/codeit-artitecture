package com.example.demo.controller.api.dto;

import com.example.demo.repository.product.Product;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class ProductAdminUpsertRequestDto extends RequestingUserDto {
//  @NotNull  // ① null X
//  @NotEmpty // ① null X ② "" X
    @NotBlank // ① null X ② "" X ③ " " X
    private final String name;
    @Min(10000)
    private final int price;
    @Max(100)
    private final int stock;

    public ProductAdminUpsertRequestDto(String name, int price, int stock, Integer requestUserId) {
        super(requestUserId);
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Product to() {
        return Product.create(this.name, this.price, this.stock, super.requestUserId);
    }
}
