package com.sitrack.sitrackbackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @Setter
    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @Setter
    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Setter
    @Column(nullable = false)
    private Long orderItemQuantity; // 주문 상품 수량

    @Setter
    @Column(nullable = false)
    private Long orderItemPrice; // 주문 상품 가격

    public OrderItem(Product product, Long orderItemQuantity, Long orderItemPrice) {
        this.product = product;
        this.orderItemQuantity = orderItemQuantity;
        this.orderItemPrice = orderItemPrice;
    }

    protected OrderItem() {}

    public static OrderItem of(Product product, Long orderItemQuantity, Long orderItemPrice){
        return new OrderItem(product, orderItemQuantity, orderItemPrice);
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
