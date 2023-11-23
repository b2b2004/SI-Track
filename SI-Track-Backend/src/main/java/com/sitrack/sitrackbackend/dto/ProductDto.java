package com.sitrack.sitrackbackend.dto;

import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.account.UserAccount;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ProductDto(
        Long productId,
        UserAccountDto userAccountdto,
        List<ProductImageDto> productImageDtos,
        CategoryDto categoryDto,
        SupplierDto supplierDto,
        String productName,
        Long productCost,
        Long productPrice,
        String productDetail,
        Long productStockQuantity,
        Long productSalesQuantity,
        LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy){

    public static ProductDto of(Long productId, UserAccountDto userAccountdto, CategoryDto categoryDto, SupplierDto supplierDto, String productName, Long productCost,
                                Long productPrice, String productDetail, Long productStockQuantity, Long productSalesQuantity,
                                LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy){
        return new ProductDto(productId, userAccountdto, null, categoryDto, supplierDto, productName, productCost, productPrice, productDetail, productStockQuantity, productSalesQuantity, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ProductDto of(UserAccountDto userAccountdto, CategoryDto categoryDto, SupplierDto supplierDto, String productName, Long productCost,
                                Long productPrice, String productDetail, Long productStockQuantity, Long productSalesQuantity){
        return new ProductDto(null, userAccountdto, null, categoryDto, supplierDto, productName, productCost, productPrice, productDetail, productStockQuantity, productSalesQuantity, null, null, null, null);
    }

    public static ProductDto of(UserAccountDto userAccountDto,CategoryDto categoryDto, SupplierDto supplierDto, String productName, String productDetail) {
        return new ProductDto(null, userAccountDto, null, categoryDto,  supplierDto, productName, null, null, productDetail, null, null, null, null, null, null);
    }

    public static ProductDto from(Product entity) {
        return new ProductDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getProductImages().stream().map(ProductImageDto::from)
                        .collect(Collectors.toList()),
                CategoryDto.from(entity.getCategory()),
                SupplierDto.from(entity.getSupplier()),
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

    public static ProductDto from1(Product entity) {
        return new ProductDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                null,
                CategoryDto.from(entity.getCategory()),
                SupplierDto.from(entity.getSupplier()),
                entity.getProductName(),
                entity.getProductCost(),
                entity.getProductPrice(),
                null,
                entity.getProductStockQuantity(),
                entity.getProductSalesQuantity(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

//    public Product toEntity(UserAccount userAccount, CategoryDto categoryDto, SupplierDto supplierDto){
//        return Product.of(
//                userAccount,
//                categoryId,
//                supplierCode,
//                productName,
//                productCost,
//                productPrice,
//                productDetail,
//                productStockQuantity,
//                productSalesQuantity
//        );
//    }



}
