package com.example.demo.repository.product;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepository {
    private final static Map<Integer, Product> PRODUCTS = new HashMap<>();
}
