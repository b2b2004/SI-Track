package com.sitrack.sitrackbackend.dto;

import com.sitrack.sitrackbackend.domain.OrderItem;

public record OrderItemDto(
        Long productId,
        Long quantity,
        Long price
) {
    public static OrderItemDto of(Long productId, Long quantity, Long price){
        return new OrderItemDto(productId, quantity, price);
    }

    public static OrderItemDto from(OrderItem entity){
        return new OrderItemDto(
                entity.getId(),
                entity.getOrderItemQuantity(),
                entity.getOrderItemPrice()
        );
    }
}
