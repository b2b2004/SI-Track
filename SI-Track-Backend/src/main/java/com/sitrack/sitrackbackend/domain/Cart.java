package com.sitrack.sitrackbackend.domain;

import com.sitrack.sitrackbackend.domain.account.UserAccount;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
public class Cart extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @JoinColumn(name = "userId")
    @ManyToOne
    private UserAccount userAccount;

    @Setter
    @JoinColumn(name = "id")
    @ManyToOne
    private Product productId;

    @Setter
    @Column(nullable = false)
    private Long CartQuantity; // 상품 수량

    @Setter
    @Column
    private Enum isPaid;

    protected Cart(){}

    public Cart(Long id, UserAccount userAccount, Product productId, Long cartQuantity, Enum isPaid) {
        this.id = id;
        this.userAccount = userAccount;
        this.productId = productId;
        CartQuantity = cartQuantity;
        this.isPaid = isPaid;
    }

    public Cart of(Long id, UserAccount userAccount, Product productId, Long cartQuantity, Enum isPaid){
        return new Cart(id, userAccount, productId, cartQuantity, isPaid);
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
