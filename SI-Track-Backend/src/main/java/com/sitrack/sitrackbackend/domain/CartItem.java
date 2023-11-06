package com.sitrack.sitrackbackend.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString(callSuper = true)
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cartItem_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product product;

    @Setter
    @Column(nullable = false)
    private Long quantity; // 상품 개수

    public void addQuantity(Long quantity) {
        this.quantity += quantity;
    }

    protected CartItem(){}

    public CartItem(Cart cart, Product product, Long quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public static CartItem of(Cart cart, Product product, Long cartQuantity){
        return new CartItem(cart, product, cartQuantity);
    }
}
