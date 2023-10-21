package com.sitrack.sitrackbackend.dto.request;

import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.UserAccountDto;

public record ProductUpdateRequest(
        Long categoryId,
        String supplierCode,
        String productName,
        String productDetail,
        String userId
) {

    public ProductDto toDto(UserAccountDto userAccountDto){
        return ProductDto.of(
                userAccountDto,
                categoryId,
                supplierCode,
                productName,
                productDetail
        );
    }

}
