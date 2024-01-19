package com.sitrack.sitrackbackend.dto.response;

import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.ProductImageDto;

import java.util.List;
import java.util.stream.Collectors;

public record ProductOne(
        Long productId,
        String supplierCode,
        String productName,
        Long productPrice,
        String productDetail,
        List<String> productImageUrl
) {

    public static ProductOne of(Long productId, String supplierCode, String productName, Long productPrice, String productDetail, String productImageUrl){
        return new ProductOne(productId, supplierCode, productName, productPrice, productDetail, null);
    }

    public static ProductOne from(ProductDto dto) {
        return new ProductOne(
                dto.productId(),
                dto.supplierDto().supplierCode(),
                dto.productName(),
                dto.productPrice(),
                dto.productDetail(),
                dto.productImageDtos().stream().map(ProductImageDto::getSaveName).collect(Collectors.toList())
        );
    }
}

