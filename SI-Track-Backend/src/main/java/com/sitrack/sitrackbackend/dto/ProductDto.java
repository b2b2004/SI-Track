package com.sitrack.sitrackbackend.dto;

import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.ProductImage;
import com.sitrack.sitrackbackend.domain.account.UserAccount;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ProductDto(
        Long productId,
        UserAccountDto userAccountdto,
        List<ProductImageDto> productImageDtos,
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
        return new ProductDto(productId, userAccountdto, null, categoryId, supplierCode, productName, productCost, productPrice, productDetail, productStockQuantity, productSalesQuantity, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ProductDto of(UserAccountDto userAccountdto, Long categoryId, String supplierCode, String productName, Long productCost,
                                Long productPrice, String productDetail, Long productStockQuantity, Long productSalesQuantity){
        return new ProductDto(null, userAccountdto, null, categoryId, supplierCode, productName, productCost, productPrice, productDetail, productStockQuantity, productSalesQuantity, null, null, null, null);
    }

    public static ProductDto of(UserAccountDto userAccountDto, Long categoryId, String supplierCode, String productName, String productDetail) {
        return new ProductDto(null, userAccountDto, null, categoryId,  supplierCode, productName, null, null, productDetail, null, null, null, null, null, null);
    }

    public static ProductDto from(Product entity) {
        return new ProductDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getProductImages().stream().map(ProductImageDto::from)
                        .collect(Collectors.toList()),
                entity.getCategoryId(),
                entity.getSupplierCode(),
                entity.getProductName(),
                entity.getProductCost(),
                entity.getProductPrice(),
                entity.getProductDetail(),
                entity.getProductStockQuantity(),
                entity.getProductSalesQuantity(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
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
