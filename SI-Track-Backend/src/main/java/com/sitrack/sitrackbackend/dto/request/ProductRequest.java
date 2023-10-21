package com.sitrack.sitrackbackend.dto.request;

import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.UserAccountDto;

public record ProductRequest(
        Long categoryId,
        String supplierCode,
        String productName,
        Long productCost,
        Long productPrice,
        String productDetail,
        Long productStockQuantity,
        Long productSalesQuantity,
        String userId
) {

    public static ProductRequest of(Long categoryId,
                                    String supplierCode,
                                    String productName,
                                    Long productCost,
                                    Long productPrice,
                                    String productDetail,
                                    Long productStockQuantity,
                                    Long productSalesQuantity,
                                    String userId){
        return new ProductRequest(categoryId, supplierCode, productName, productCost, productPrice, productDetail, productStockQuantity, productSalesQuantity, userId);
    }

    public ProductDto toDto(UserAccountDto userAccountDto) {
        return ProductDto.of(
                userAccountDto,
                categoryId,
                supplierCode,
                productName,
                productCost,
                productPrice,
                productDetail,
                productStockQuantity,
                productSalesQuantity
        );
    }
}
