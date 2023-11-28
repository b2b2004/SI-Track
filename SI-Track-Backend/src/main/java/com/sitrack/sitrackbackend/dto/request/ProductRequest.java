package com.sitrack.sitrackbackend.dto.request;

import com.sitrack.sitrackbackend.domain.Category;
import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.Supplier;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

public record ProductRequest(

        @NotBlank(message = "categoryName is Null")
        String categoryName,

        @NotBlank(message = "supplierCode is Null")
        String supplierCode,

        @NotBlank(message = "productName is Null")
        String productName,

        @Range(min = 1, max = 10000000, message = "productCost is over Range")
        Long productCost,

        @Range(min = 1, max = 10000000, message = "productPrice is over Range")
        Long productPrice,

        String productDetail,

        @Range(max = 10000000, message = "productStockQuantity is over Range")
        Long productStockQuantity,

        @Range(max = 10000000, message = "productSalesQuantity is over Range")
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
