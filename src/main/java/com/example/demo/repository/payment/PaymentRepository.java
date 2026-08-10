package com.example.demo.repository.payment;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PaymentRepository {
    private final static Map<Integer, Payment> PAYMENTS = new HashMap<>();
}
