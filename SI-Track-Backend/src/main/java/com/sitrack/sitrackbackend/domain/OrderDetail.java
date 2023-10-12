package com.sitrack.sitrackbackend.domain;

import com.sitrack.sitrackbackend.domain.account.UserAccount;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @JoinColumn(name = "id")
    @ManyToOne
    private Product productId;

    @Setter
    @Column(nullable = false)
    private Long orderDetailQuantity; // 주문 상품 수량

    @Setter
    @Column(nullable = false)
    private Long orderDetailPrice; // 주문 상품 가격

    public OrderDetail(Product productId, Long orderDetailQuantity, Long orderDetailPrice) {
        this.productId = productId;
        this.orderDetailQuantity = orderDetailQuantity;
        this.orderDetailPrice = orderDetailPrice;
    }

    protected OrderDetail() {}

    public OrderDetail of(Product productId, Long orderDetailQuantity, Long orderDetailPrice){
        return new OrderDetail(productId, orderDetailQuantity, orderDetailPrice);
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
