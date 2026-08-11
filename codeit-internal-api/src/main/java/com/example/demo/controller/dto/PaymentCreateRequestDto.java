package com.example.demo.controller.dto;

import lombok.Getter;

import java.util.List;

@Getter
// @NoArgsConstructor + @Setter = @RequestParam 쓴다면 값을 하나씩 하나씩 채우는거라 이걸 명시해줘야함 (추측)
// @AllArgsConstructor = @RequestBody, @ModelAttribute 쓴다면 한방에 객체를 만들기때문에 이걸 명시해줘야함
public class PaymentCreateRequestDto extends RequestingUserDto {
    private List<Integer> productIds;

    public PaymentCreateRequestDto(List<Integer> productIds, Integer requestUserId) {
        super(requestUserId);
        this.productIds = productIds;
    }
}
