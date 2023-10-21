package com.sitrack.sitrackbackend.dto.response;

import com.sitrack.sitrackbackend.dto.ProductDto;
import org.springframework.security.core.parameters.P;

public record ProductResponse(
        String supplierCode,
        String productName,
        Long productCost,
        String productDetail
        // 이미지 추가 예정
) {

    public static ProductResponse of(String supplierCode, String productName, Long productCost, String productDetail){
        return new ProductResponse(supplierCode, productName, productCost, productDetail);
    }

    public static ProductResponse from(ProductDto dto) {
        return new ProductResponse(
                dto.supplierCode(),
                dto.productName(),
                dto.productCost(),
                dto.productDetail()
        );
    }
}
