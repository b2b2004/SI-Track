package com.sitrack.sitrackbackend.dto.response;

import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.ProductImageDto;
import java.util.List;
import java.util.stream.Collectors;

public record ProductUpdateResponse(
        String supplierCode,
        String categoryName,
        String productName,
        String productDetail,
        List<String> productImageUrl
) {

    public static ProductUpdateResponse from(ProductDto dto) {
        return new ProductUpdateResponse(
                dto.supplierDto().supplierCode(),
                dto.categoryDto().categoryName(),
                dto.productName(),
                dto.productDetail(),
                dto.productImageDtos().stream().map(ProductImageDto::getSaveName).collect(Collectors.toList())
        );
    }

}
