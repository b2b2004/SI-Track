package com.sitrack.sitrackbackend.dto.request;

import com.sitrack.sitrackbackend.domain.ProductImage;
import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ProductRequest(
        Long categoryId,
        String supplierCode,
        String productName,
        Long productCost,
        Long productPrice,
        String productDetail,
        Long productStockQuantity,
        Long productSalesQuantity
){

    public static ProductRequest of(Long categoryId,
                                    String supplierCode,
                                    String productName,
                                    Long productCost,
                                    Long productPrice,
                                    String productDetail,
                                    Long productStockQuantity,
                                    Long productSalesQuantity){
        return new ProductRequest(categoryId, supplierCode, productName, productCost, productPrice, productDetail, productStockQuantity, productSalesQuantity);
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
