package com.sitrack.sitrackbackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
public class PurchaseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @JoinColumn(name = "product_id")
    @ManyToOne(optional = false)
    private Product product;

    @Setter
    @JoinColumn(name = "purchase_id")
    @ManyToOne(optional = false)
    private Purchase purchase;

    @Setter
    @Column(nullable = false)
    private Long purchaseDetailQuantity; // 주문 상품 수량

    @Setter
    @Column(nullable = false)
    private Long purchaseDetailPrice; // 주문 상품 가격

    public PurchaseDetail(Product product, Purchase purchase, Long purchaseDetailQuantity, Long purchaseDetailPrice) {
        this.product = product;
        this.purchase = purchase;
        this.purchaseDetailQuantity = purchaseDetailQuantity;
        this.purchaseDetailPrice = purchaseDetailPrice;
    }

    protected PurchaseDetail() {}

    public static PurchaseDetail of(Product product, Purchase purchase, Long purchaseDetailQuantity, Long purchaseDetailPrice){
        return new PurchaseDetail(product, purchase, purchaseDetailQuantity, purchaseDetailPrice);
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
