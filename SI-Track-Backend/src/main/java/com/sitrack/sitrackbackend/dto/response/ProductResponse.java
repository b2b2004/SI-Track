package com.sitrack.sitrackbackend.dto.response;

import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.ProductImageDto;

import java.util.List;
import java.util.stream.Collectors;

public record ProductResponse(
        Long productId,
        String supplierCode,
        String productName,
        Long productCost,
        String productDetail,
        List<String> productImagesUrl
) {

    public static ProductResponse of(Long productId, String supplierCode, String productName, Long productCost, String productDetail){
        return new ProductResponse(productId, supplierCode, productName, productCost, productDetail, null);
    }

    public static ProductResponse from(ProductDto dto) {
        return new ProductResponse(
                dto.productId(),
                dto.supplierDto().supplierCode(),
                dto.productName(),
                dto.productCost(),
                dto.productDetail(),
                dto.productImageDtos().stream().map(ProductImageDto::getSaveName).collect(Collectors.toList())
        );
    }
}
