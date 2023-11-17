package com.sitrack.sitrackbackend.dto;

import com.sitrack.sitrackbackend.domain.Product;
import com.sun.istack.NotNull;

import java.time.LocalDateTime;

public record AdminProductDto(
        @NotNull Long productId,
        @NotNull UserAccountDto userAccountdto,
        @NotNull Long categoryId,
        @NotNull String supplierCode,
        @NotNull String productName,
        @NotNull Long productCost,
        @NotNull Long productPrice,
        @NotNull Long productStockQuantity,
        @NotNull Long productSalesQuantity,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy) {


    public static AdminProductDto from(Product entity) {
        return new AdminProductDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getCategoryId(),
                entity.getSupplierCode(),
                entity.getProductName(),
                entity.getProductCost(),
                entity.getProductPrice(),
                entity.getProductStockQuantity(),
                entity.getProductSalesQuantity(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public static AdminProductDto of(Long productId, UserAccountDto userAccountdto, Long categoryId, String supplierCode, String productName, Long productCost, Long productPrice, Long productStockQuantity, Long productSalesQuantity) {
        return new AdminProductDto(productId, userAccountdto, categoryId, supplierCode, productName, productCost, productPrice, productStockQuantity, productSalesQuantity, null, null, null, null);
    }
}
