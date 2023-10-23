package com.sitrack.sitrackbackend.dto;

import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.ProductImage;
import com.sitrack.sitrackbackend.domain.constant.ProductImageType;

public record ProductImageDto(
        String originalName,
        String saveName,
        String imagePath,
        ProductImageType imageType
) {

    public static ProductImageDto of(String originalName, String saveName, String imagePath, ProductImageType imageType){
        return new ProductImageDto(originalName, saveName, imagePath, imageType);
    }

    public ProductImage toEntity(Product product, ProductImageDto productImageDto){
        return ProductImage.of(
                product,
                productImageDto.originalName,
                productImageDto.saveName,
                productImageDto.imagePath,
                productImageDto.imageType
        );
    }

}
