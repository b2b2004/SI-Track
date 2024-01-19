package com.sitrack.sitrackbackend.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

public record AdminProductDto(

        @NotBlank(message = "productId is Null")
        Long productId,

        @NotBlank(message = "userId is Null")
        String userId,

        @NotBlank(message = "categoryId is Null")
        Long categoryId,

        @NotBlank(message = "categoryName is Null")
        String categoryName,

        @NotBlank(message = "supplierId is Null")
        Long supplierId,

        @NotBlank(message = "supplierName is Null")
        String supplierName,

        @NotBlank(message = "supplierCode is Null")
        String supplierCode,

        @NotBlank(message = "productName is Null")
        String productName,

        @Range(min = 1, max = 10000000, message = "productCost is over Range")
        Long productCost,

        @Range(min = 1, max = 10000000, message = "productPrice is over Range")
        Long productPrice,

        @Range(max = 10000000, message = "productStockQuantity is over Range")
        Long productStockQuantity,

        @Range(max = 10000000, message = "productSalesQuantity is over Range")
        Long productSalesQuantity
) {


        public static AdminProductDto of(Long productId, String userId, Long categoryId, String categoryName, Long supplierId, String supplierName, String supplierCode, String productName, Long productCost, Long productPrice, Long productStockQuantity, Long productSalesQuantity) {
                return new AdminProductDto(productId, userId, categoryId, categoryName, supplierId, supplierName, supplierCode, productName, productCost, productPrice, productStockQuantity, productSalesQuantity);
        }


}
