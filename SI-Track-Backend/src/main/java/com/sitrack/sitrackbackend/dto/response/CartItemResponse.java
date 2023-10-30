package com.sitrack.sitrackbackend.dto.response;

import com.sitrack.sitrackbackend.domain.CartItem;
import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.ProductImage;
import com.sitrack.sitrackbackend.dto.ProductImageDto;

import java.util.List;

import static com.sitrack.sitrackbackend.domain.constant.ProductImageType.Subnail;

public record CartItemResponse(
        Long cartItemId,
        Long productId,
        String productName,
        String productDetail,
        Long productPrice,
        String productimage,
        Long amount
) {
    public static CartItemResponse toDto(CartItem cartItem, Long amount) {
        Product product = cartItem.getProduct();
        List<ProductImage> productImages = product.getProductImages();
        String a = "";
        for(ProductImage image : productImages){
            if(image.getImageType() == Subnail){
                a = image.getSaveName();
            }
        }
        return new CartItemResponse(cartItem.getId(), product.getId(), product.getProductName(), product.getProductDetail(), product.getProductPrice(), a, amount);
    }


}