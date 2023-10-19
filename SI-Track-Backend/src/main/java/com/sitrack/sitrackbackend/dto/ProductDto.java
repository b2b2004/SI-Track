package com.sitrack.sitrackbackend.dto;

import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.account.UserAccount;

import java.time.LocalDateTime;

public record ProductDto(
        Long productId,
        UserAccountDto userAccountdto,
        Long categoryId,
        String supplierCode,
        String productName,
        Long productCost,
        Long productPrice,
        String productDetail,
        Long productStockQuantity,
        Long productSalesQuantity,
        LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy){

    public static ProductDto of(Long productId, UserAccountDto userAccountdto, Long categoryId, String supplierCode, String productName, Long productCost,
                                Long productPrice, String productDetail, Long productStockQuantity, Long productSalesQuantity,
                                LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy){
        return new ProductDto(productId, userAccountdto, categoryId, supplierCode, productName, productCost, productPrice, productDetail, productStockQuantity, productSalesQuantity, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ProductDto of(UserAccountDto userAccountdto, Long categoryId, String supplierCode, String productName, Long productCost,
                                Long productPrice, String productDetail, Long productStockQuantity, Long productSalesQuantity){
        return new ProductDto(null, userAccountdto, categoryId, supplierCode, productName, productCost, productPrice, productDetail, productStockQuantity, productSalesQuantity, null, null, null, null);
    }

    public Product toEntity(UserAccount userAccount){
        return Product.of(
                userAccount,
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
