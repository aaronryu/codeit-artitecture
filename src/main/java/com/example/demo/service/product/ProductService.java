package com.example.demo.service.product;

import com.example.demo.repository.IRepository;
import com.example.demo.repository.product.Product;
import com.example.demo.repository.product.ProductRepository;
import com.example.demo.repository.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

/**
 * ProductService
 *  - Service 명칭 자체가 기본적으로 도메인 서비스를 의미 Domain Service
 *      = Domain Service <- Domain Repository
 *  - Product 도메인(엔티티 객체)을 파라미터로 받거나 반환값으로 반환하는
 *      = ProductService 와 외부 Application 의 관계는
 *          - 외부 Application 에게 Product 반환해주거나
 *          - 외부 Application 로부터 Product 받아서 그것에 대한 처리를 해주거나
 *              * 처리 : CRUD 에 국한된다 / 주의 ! 도메인 내부 상태를 바꾸는 메서드는 Application 에서 호출할것 !
 *  - Domain Service 서비스의 강제사항은 단 하나의 Domain Repository 만 필드로 가져야한다는것
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    /**
     * Hexagonal (Port and Adaptor) 아키텍쳐 도입 시
     *  - Controller <= Primary Adaptor = Driving Adaptor
     *  - Application 인터페이스 <= Input Port
     *  - Repository 인터페이스 <= Output Port
     *  - Repository 구체클래스 <= Secondary Adaptor = Driven Adaptor
     */
    private final IRepository<Integer, Product> productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Integer id) {
        Optional<Product> wrappedProduct = productRepository.findById(id);
                 Product         product = wrappedProduct
                         .orElseThrow(() -> new RuntimeException("찾으시는 유저가 존재하지 않습니다"));
        return product;
    }

    public Optional<Product> findProduct(Integer id) {
        return productRepository.findById(id);
    }

    public Product create(Product entity) {
        Optional<Product> wrappedCreated = productRepository.create(entity);
                 Product         created = wrappedCreated
                         .orElseThrow(() -> new RuntimeException("상품이 정상적으로 생성되지 않습니다"));
        return created;
    }

    public List<Product> update(List<Product> entities) {
        return entities.stream()
                .map(this::update)
                .toList();
    }

    public Product update(Product entity) {
        Optional<Product> wrappedProduct = productRepository.update(entity);
                 Product         product = wrappedProduct
                         .orElseThrow(() -> new RuntimeException("업데이트가 정상적으로 되지 않습니다"));
        return product;
    }

    public void active(Integer id) {
        Product exist = this.getProduct(id);
        exist.active();
        productRepository.update(exist);
    }

    public void softDelete(Integer id) {
        Product exist = this.getProduct(id);
        exist.delete();
        productRepository.update(exist);
    }

    public void hardDelete(Integer id) {
        Product exist = this.getProduct(id);
        productRepository.delete(id);
    }
}
