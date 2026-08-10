package com.example.demo.application.product;

import com.example.demo.controller.internal.api.dto.ProductResponseDto;
import com.example.demo.repository.product.Product;
import com.example.demo.repository.product.ProductRepository;
import com.example.demo.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductApplication implements IProductApplication {
    private final ProductService productService;

    public List<ProductResponseDto> retrieve() {
        List<Product> products = productService.getProducts();
        return products.stream()
                .map(ProductResponseDto::from)
                .toList();
    }

    public ProductResponseDto retrieve(Integer id) {
        Product retrieved = productService.getProduct(id);
        return ProductResponseDto.from(retrieved);
    }
}
