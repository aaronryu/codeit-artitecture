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
    @Max(value = 100, message = "현재 창고에는 한 상품당 100개 넘게 적재할 수 없습니다. 100보다 적은 수를 입력해주세요")
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
