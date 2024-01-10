package com.sitrack.sitrackbackend.dto;

import com.sitrack.sitrackbackend.domain.Product;

public record OrderItemListDto(
        String productName,
        String productDetail,
        Long productPrice,
        String productImage,
        Long amount
) {

    public static OrderItemListDto of(Product product, Long amount){
        return new OrderItemListDto(
                product.getProductName(),
                product.getProductDetail(),
                product.getProductPrice(),
                product.getThumbnailImage(),
                amount
        );
    }
}
