package com.example.demo.application.product;

import com.example.demo.controller.dto.ProductResponseDto;

import java.util.List;

public interface IProductApplication {

    List<ProductResponseDto> retrieve();

    ProductResponseDto retrieve(Integer id);
}
