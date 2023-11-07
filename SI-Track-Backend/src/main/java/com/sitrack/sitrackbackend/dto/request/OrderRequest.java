package com.sitrack.sitrackbackend.dto.request;

import com.sitrack.sitrackbackend.dto.OrderItemDto;

import java.util.List;

public record OrderRequest(
        Long totalAmount,       // 총 가격
        String orderAddress,    // 주소
        String orderRequest,    // 요구 사항
        String recipient,       // 수령인
        String phoneNumber,     // 수령인 핸드폰 번호
        List<OrderItemDto> orderItemDtos
) {
    public static OrderRequest of(Long totalAmount, String orderAddress, String orderRequest, String recipient, String phoneNumber, List<OrderItemDto> orderItemDtos) {
        return new OrderRequest(totalAmount, orderAddress, orderRequest, recipient, phoneNumber, orderItemDtos);
    }
}
