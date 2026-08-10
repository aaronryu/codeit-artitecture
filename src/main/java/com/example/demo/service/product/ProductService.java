package com.example.demo.service.product;

import com.example.demo.internal.api.dto.ProductResponseDto;
import com.example.demo.repository.product.Product;
import com.example.demo.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductResponseDto> retrieve() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductResponseDto::from)
                .toList();
    }

    public ProductResponseDto retrieve(Integer id) {
        Optional<Product> wrappedProduct = productRepository.findById(id);
                 Product         product = wrappedProduct
                .orElseThrow(() -> new RuntimeException("찾으시는 유저가 존재하지 않습니다"));
        return ProductResponseDto.from(product);
    }
}
