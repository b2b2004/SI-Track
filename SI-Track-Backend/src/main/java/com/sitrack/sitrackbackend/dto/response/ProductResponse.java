package com.sitrack.sitrackbackend.dto.response;

import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.ProductImageDto;

import java.util.List;

public record ProductResponse(
        Long productId,
        String supplierCode,
        String productName,
        Long productCost,
        String productDetail,
        List<ProductImageDto> productImageDtos
) {

    public static ProductResponse of(Long productId, String supplierCode, String productName, Long productCost, String productDetail, List<ProductImageDto> productImageDtos){
        return new ProductResponse(productId, supplierCode, productName, productCost, productDetail, productImageDtos);
    }

    public static ProductResponse from(ProductDto dto) {
        return new ProductResponse(
                dto.productId(),
                dto.supplierDto().supplierCode(),
                dto.productName(),
                dto.productCost(),
                dto.productDetail(),
                dto.productImageDtos()
        );
    }
}
