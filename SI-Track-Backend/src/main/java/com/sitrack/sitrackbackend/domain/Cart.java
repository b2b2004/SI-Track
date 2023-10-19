package com.sitrack.sitrackbackend.domain;

import com.sitrack.sitrackbackend.domain.account.UserAccount;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
public class Cart extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    // userid 추가
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    // productid 추가
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;


    @Setter
    @Column(nullable = false)
    private Long cartQuantity; // 상품 수량

    @Setter
    @Column
    private Enum isPaid;

    protected Cart(){}

    public Cart(Long id, UserAccount userAccount, Product product, Long cartQuantity, Enum isPaid) {
        this.id = id;
        this.userAccount = userAccount;
        this.product = product;
        this.cartQuantity = cartQuantity;
        this.isPaid = isPaid;
    }

    public static Cart of(Long id, UserAccount userAccount, Product product, Long cartQuantity, Enum isPaid){
        return new Cart(id, userAccount, product, cartQuantity, isPaid);
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
