package com.sitrack.sitrackbackend.dto.response;

import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.ProductImageDto;
import org.springframework.security.core.parameters.P;

import java.util.List;

public record ProductResponse(
        String supplierCode,
        String productName,
        Long productCost,
        String productDetail,
        List<ProductImageDto> productImageDtos
) {

    public static ProductResponse of(String supplierCode, String productName, Long productCost, String productDetail, List<ProductImageDto> productImageDtos){
        return new ProductResponse(supplierCode, productName, productCost, productDetail, productImageDtos);
    }

    public static ProductResponse from(ProductDto dto) {
        return new ProductResponse(
                dto.supplierCode(),
                dto.productName(),
                dto.productCost(),
                dto.productDetail(),
                dto.productImageDtos()
        );
    }
}
