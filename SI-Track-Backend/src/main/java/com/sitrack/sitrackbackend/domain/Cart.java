package com.sitrack.sitrackbackend.domain;

import com.sitrack.sitrackbackend.domain.account.UserAccount;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.catalina.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Cart{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 생성일시

    @Setter
    private Long cartQuantity; // 상품 수량

    // 연관관계 메서드
    public void serUserAccout(UserAccount userAccount) {
        this.userAccount = userAccount;
        userAccount.setCart(this);
    }

    public void addcartItem(CartItem cartItem){
        cartItems.add(cartItem);
        cartItem.setCart(this);
    }

    protected Cart(){}
    public Cart(UserAccount user) {
        this.userAccount = user;
    }

    public Cart(UserAccount userAccount, Long cartQuantity) {
        this.userAccount = userAccount;
        this.cartQuantity = cartQuantity;
    }

    public static Cart of(UserAccount userAccount, Long cartQuantity){
        return new Cart(userAccount, cartQuantity);
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
