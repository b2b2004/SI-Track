package com.sitrack.sitrackbackend.dto.request;

public record CartItemRequest(
        Long productId,
        Long quantity
) {

    public static CartItemRequest of(Long productId, Long quantity) {
        return new CartItemRequest(productId, quantity);
    }
}
