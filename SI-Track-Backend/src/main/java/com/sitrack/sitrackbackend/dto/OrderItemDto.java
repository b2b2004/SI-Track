package com.sitrack.sitrackbackend.dto;

public record OrderItemDto(
        Long productId,
        Long quantity
) {
    public static OrderItemDto of(Long productId, Long quantity){
        return new OrderItemDto(productId, quantity);
    }
}
