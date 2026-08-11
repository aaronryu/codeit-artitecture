package com.example.demo.application.payment;

import com.example.demo.controller.dto.PaymentResponseDto;

import java.util.List;

public interface IPaymentApplication {

    PaymentResponseDto payment(List<Integer> productIds);

    PaymentResponseDto cancel(Integer id);
}
