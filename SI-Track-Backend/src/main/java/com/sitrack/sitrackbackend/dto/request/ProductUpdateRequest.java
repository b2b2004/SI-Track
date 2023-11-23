package com.sitrack.sitrackbackend.dto.request;

public record ProductUpdateRequest(
        String categoryName,
        String supplierCode,
        String productName,
        String productDetail
) {

    public static ProductUpdateRequest of(String categoryName, String supplierCode, String productName, String productDetail){
        return new ProductUpdateRequest(categoryName, supplierCode, productName, productDetail);
    }
}
