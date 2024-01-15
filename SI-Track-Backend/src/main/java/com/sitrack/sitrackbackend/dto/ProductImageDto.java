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

    public static ProductImageDto from(ProductImage entity) {
        return new ProductImageDto(
                entity.getOriginalName(),
                entity.getSaveName(),
                entity.getImagePath(),
                entity.getImageType()
        );
    }

    public static String getSaveName(ProductImageDto entity){
        return entity.imagePath + entity.saveName;
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
