package com.sitrack.sitrackbackend.dto.response;

import com.sitrack.sitrackbackend.dto.CategoryDto;
import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.ProductImageDto;
import com.sitrack.sitrackbackend.dto.SupplierDto;

import java.util.List;

public record ProductUpdateResponse(
        SupplierDto supplierDto,
        CategoryDto categoryDto,
        String productName,
        Long productCost,
        String productDetail,
        List<ProductImageDto> productImageDtos
) {

    public static ProductUpdateResponse from(ProductDto dto) {
        return new ProductUpdateResponse(
                dto.supplierDto(),
                dto.categoryDto(),
                dto.productName(),
                dto.productCost(),
                dto.productDetail(),
                dto.productImageDtos()
        );
    }

}
