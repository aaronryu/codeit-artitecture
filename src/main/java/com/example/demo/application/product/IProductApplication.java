package com.example.demo.application.product;

import com.example.demo.controller.internal.api.dto.ProductResponseDto;
import com.example.demo.repository.product.Product;
import com.example.demo.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IProductApplication {

    List<ProductResponseDto> retrieve();

    ProductResponseDto retrieve(Integer id);
}
