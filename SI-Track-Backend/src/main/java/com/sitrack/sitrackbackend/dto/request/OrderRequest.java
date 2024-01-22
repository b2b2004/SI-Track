package com.sitrack.sitrackbackend.dto.request;

import com.sitrack.sitrackbackend.dto.OrderItemDto;

import javax.validation.constraints.NotBlank;
import java.util.List;

public record OrderRequest(
        Long totalAmount,       // 총 가격

        @NotBlank(message = "orderAddress is Null")
        String orderAddress,    // 주소

        @NotBlank(message = "orderRequest is Null")
        String orderRq,    // 요구 사항

        @NotBlank(message = "recipient is Null")
        String recipient,       // 수령인

        @NotBlank(message = "phoneNumber is Null")
        String phoneNumber,     // 수령인 핸드폰 번호

        List<OrderItemDto> orderItemDtos
) {
    public static OrderRequest of(Long totalAmount, String orderAddress, String orderRequest, String recipient, String phoneNumber, List<OrderItemDto> orderItemDtos) {
        return new OrderRequest(totalAmount, orderAddress, orderRequest, recipient, phoneNumber, orderItemDtos);
    }
}
