package com.sitrack.sitrackbackend.dto;

import com.sitrack.sitrackbackend.domain.OrderItem;
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

    public static OrderItemListDto from(OrderItem orderItem){
        return new OrderItemListDto(
                orderItem.getProduct().getProductName(),
                orderItem.getProduct().getProductDetail(),
                orderItem.getProduct().getProductPrice(),
                orderItem.getProduct().getThumbnailImage(),
                orderItem.getOrderItemQuantity()
        );
    }
}
