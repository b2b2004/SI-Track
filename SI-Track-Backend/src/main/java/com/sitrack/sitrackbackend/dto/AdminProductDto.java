package com.sitrack.sitrackbackend.dto;

import com.sitrack.sitrackbackend.domain.Product;
import org.hibernate.validator.constraints.Range;


import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record AdminProductDto(

        Long productId,

        UserAccountDto userAccountdto,

        CategoryDto category,

        SupplierDto supplier,

        @NotBlank(message = "productName is Null")
        String productName,

        @Range(min = 1, max = 10000000, message = "productCost is over Range")
        Long productCost,

        @Range(min = 1, max = 10000000, message = "productPrice is over Range")
        Long productPrice,

        @Range(max = 10000000, message = "productStockQuantity is over Range")
        Long productStockQuantity,

        @Range(max = 10000000, message = "productSalesQuantity is over Range")
        Long productSalesQuantity,

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
