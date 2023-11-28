package com.sitrack.sitrackbackend.dto.request;

import org.hibernate.validator.constraints.Range;

public record CartItemRequest(
        @Range(min = 1, max = 10000000, message = "productId is over Range")
        Long productId,

        @Range(min = 1, max = 3000, message = "quantity is over Range")
        Long quantity
) {

    public static CartItemRequest of(Long productId, Long quantity) {
        return new CartItemRequest(productId, quantity);
    }
}
