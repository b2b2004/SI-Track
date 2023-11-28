package com.sitrack.sitrackbackend.dto.request;

import javax.validation.constraints.NotBlank;

public record ProductUpdateRequest(
        @NotBlank(message = "categoryName is Null") String categoryName,
        @NotBlank(message = "supplierCode is Null") String supplierCode,
        @NotBlank(message = "productName is Null") String productName,
        @NotBlank(message = "productDetail is Null") String productDetail
) {

    public static ProductUpdateRequest of(String categoryName, String supplierCode, String productName, String productDetail){
        return new ProductUpdateRequest(categoryName, supplierCode, productName, productDetail);
    }
}
