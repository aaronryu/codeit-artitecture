package com.example.demo.application.product;

import com.example.demo.controller.api.dto.ProductAdminResponseDto;
import com.example.demo.controller.api.dto.ProductAdminUpsertRequestDto;
import com.example.demo.repository.product.Product;
import com.example.demo.service.product.ProductService;
import com.example.demo.multipart.MultipartFileUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductAdminApplication {
    private final ProductService productService;
    private final MultipartFileUpload multipartFileUpload;

    public List<ProductAdminResponseDto> retrieve() {
        List<Product> products = productService.getProducts();
        return products.stream()
                .map(ProductAdminResponseDto::from)
                .toList();
    }

    public ProductAdminResponseDto retrieve(Integer id) {
        Product retrieved = productService.getProduct(id);
        return ProductAdminResponseDto.from(retrieved);
    }

    public ProductAdminResponseDto create(ProductAdminUpsertRequestDto request, MultipartFile thumbnail) {
        Product creating = request.to();
        if (Objects.nonNull(thumbnail)) {
            String thumbnailPath = multipartFileUpload.upload(thumbnail);
            creating.upload(thumbnailPath);
        }
        Product created = productService.create(creating);
        return ProductAdminResponseDto.from(created);
    }

    public ProductAdminResponseDto update(Integer id, ProductAdminUpsertRequestDto request, MultipartFile thumbnail) {
        Product updating = productService.getProduct(id);
        updating.update(request.getName(), request.getPrice(), request.getStock());
        if (Objects.nonNull(thumbnail)) {
            String thumbnailPath = multipartFileUpload.upload(thumbnail);
            updating.upload(thumbnailPath);
        }
        Product updated = productService.update(updating);
        return ProductAdminResponseDto.from(updated);
    }

    public void active(Integer id) {
        productService.active(id);
    }

    public void softDelete(Integer id) {
        productService.softDelete(id);
    }

    public void hardDelete(Integer id) {
        productService.hardDelete(id);
    }
}
