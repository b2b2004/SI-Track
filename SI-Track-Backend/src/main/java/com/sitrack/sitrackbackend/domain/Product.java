package com.sitrack.sitrackbackend.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
public class Product extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: 카테고리 아이디, 납품 회사 코드, 상품 이미지 추후 추가

    @Setter
    @Column(length = 50, nullable = false)
    private String productName;

    @Setter
    @Column(nullable = false)
    private Long productCost; // 상품 원가

    @Setter
    @Column(nullable = false)
    private Long productPrice; // 상품 판매 가격

    @Setter
    @Column(length = 65535)
    private String productDetail;

    @Setter
    @Column(nullable = false)
    private Long productStockQuantity; // 상품 재고 수량

    @Setter
    @Column(nullable = false)
    private Long productSalesQuantity; // 상품 판매 수량

    public Product(String productName, Long productCost, Long productPrice, String productDetail, Long productStockQuantity, Long productSalesQuantity) {
        this.productName = productName;
        this.productCost = productCost;
        this.productPrice = productPrice;
        this.productDetail = productDetail;
        this.productStockQuantity = productStockQuantity;
        this.productSalesQuantity = productSalesQuantity;
    }

    protected Product() {}

    public Product of(String productName, Long productCost, Long productPrice, String productDetail, Long productStockQuantity, Long productSalesQuantity){
        return new Product(productName, productCost, productPrice, productDetail, productStockQuantity, productSalesQuantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
