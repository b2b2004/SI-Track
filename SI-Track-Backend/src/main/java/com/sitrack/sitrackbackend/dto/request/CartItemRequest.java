package com.sitrack.sitrackbackend.dto.request;

public record CartItemRequest(
        Long productId,
        Long quantity
) {

}
