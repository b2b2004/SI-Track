package com.sitrack.sitrackbackend.dto.request;

import com.sitrack.sitrackbackend.domain.Category;
import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.Supplier;
import com.sitrack.sitrackbackend.domain.account.UserAccount;

public record ProductRequest(
        String categoryName,
        String supplierCode,
        String productName,
        Long productCost,
        Long productPrice,
        String productDetail,
        Long productStockQuantity,
        Long productSalesQuantity
){

    public static ProductRequest of(String categoryName,
                                    String supplierCode,
                                    String productName,
                                    Long productCost,
                                    Long productPrice,
                                    String productDetail,
                                    Long productStockQuantity,
                                    Long productSalesQuantity){
        return new ProductRequest(categoryName, supplierCode, productName, productCost, productPrice, productDetail, productStockQuantity, productSalesQuantity);
    }

    public Product toEntity(UserAccount userAccount, Category category, Supplier supplier) {
        return Product.of(
                userAccount,
                category,
                supplier,
                productName,
                productCost,
                productPrice,
                productDetail,
                productStockQuantity,
                productSalesQuantity
        );
    }
}
