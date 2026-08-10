package com.example.demo.application.payment;

import com.example.demo.controller.internal.api.dto.PaymentResponseDto;
import com.example.demo.repository.payment.Payment;
import com.example.demo.repository.product.Product;
import com.example.demo.service.payment.PaymentService;
import com.example.demo.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IPaymentApplication {

    PaymentResponseDto payment(List<Integer> productIds, Integer requestedUserId);

    PaymentResponseDto cancel(Integer id, Integer requestedUserId);
}
