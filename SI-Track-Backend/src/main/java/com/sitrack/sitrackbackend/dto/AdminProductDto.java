package com.sitrack.sitrackbackend.dto;

import com.sitrack.sitrackbackend.domain.Product;
import com.sun.istack.NotNull;

import java.time.LocalDateTime;

public record AdminProductDto(
        @NotNull Long productId,
        @NotNull UserAccountDto userAccountdto,
        @NotNull CategoryDto category,
        @NotNull SupplierDto supplier,
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
                CategoryDto.from(entity.getCategory()),
                SupplierDto.from(entity.getSupplier()),
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

    public static AdminProductDto of(Long productId, UserAccountDto userAccountdto, CategoryDto categoryDto, SupplierDto supplierDto, String productName, Long productCost, Long productPrice, Long productStockQuantity, Long productSalesQuantity) {
        return new AdminProductDto(productId, userAccountdto, categoryDto, supplierDto, productName, productCost, productPrice, productStockQuantity, productSalesQuantity, null, null, null, null);
    }
}
