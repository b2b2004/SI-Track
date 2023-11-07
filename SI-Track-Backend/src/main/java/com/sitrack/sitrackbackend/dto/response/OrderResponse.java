package com.sitrack.sitrackbackend.dto.response;

import com.sitrack.sitrackbackend.domain.Order;
import com.sitrack.sitrackbackend.dto.OrderItemListDto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        String orderStatus,
        LocalDateTime createDate,
        Long totalAmount,
        List<OrderItemListDto> orderItemListDtoList
) {
    public static OrderResponse of(Order order, List<OrderItemListDto> orderItemListDtos) {
        return new OrderResponse(order.getId(), order.getOrderStatus(), order.getCreatedAt(), order.getTotalAmount(), orderItemListDtos);
    }
}
