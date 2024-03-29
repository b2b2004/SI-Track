package com.sitrack.sitrackbackend.dto.response;

import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.ProductImageDto;

import java.util.List;
import java.util.stream.Collectors;

public record ProductResponse(
        Long productId,
        String supplierCode,
        String productName,
        Long productPrice,
        String productDetail,
        String productImageUrl
) {

    public static ProductResponse of(Long productId, String supplierCode, String productName, Long productPrice, String productDetail, String productImageUrl){
        return new ProductResponse(productId, supplierCode, productName, productPrice, productDetail, null);
    }

    public static ProductResponse from(ProductDto dto) {
        return new ProductResponse(
                dto.productId(),
                dto.supplierDto().supplierCode(),
                dto.productName(),
                dto.productPrice(),
                dto.productDetail(),
                null
        );
    }
}
