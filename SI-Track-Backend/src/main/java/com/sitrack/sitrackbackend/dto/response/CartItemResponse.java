package com.sitrack.sitrackbackend.dto.response;

import com.sitrack.sitrackbackend.domain.CartItem;
import com.sitrack.sitrackbackend.domain.Product;

public record CartItemResponse(
        Long cartItemId,
        Long productId,
        String productName,
        String productDetail,
        Long productPrice,
        String productImagesUrl,
        Long amount
) {

    public static CartItemResponse of(Long cartItemId, Long productId, String productName, String productDetail, Long productPrice, String productImagesUrl, Long amount) {
        return new CartItemResponse(cartItemId, productId, productName, productDetail, productPrice, productImagesUrl, amount);
    }

    public static CartItemResponse from(CartItem cartItem){
        Product product = cartItem.getProduct();
        String imageName = product.getThumbnailImage();
        return new CartItemResponse(cartItem.getId(), product.getId(), product.getProductName(), product.getProductDetail(), product.getProductPrice(), imageName, cartItem.getQuantity());
    }

    public static CartItemResponse toDto(CartItem cartItem, Long amount) {
        Product product = cartItem.getProduct();
        String imageName = product.getThumbnailImage();
        return new CartItemResponse(cartItem.getId(), product.getId(), product.getProductName(), product.getProductDetail(), product.getProductPrice(), imageName, amount);
    }

}