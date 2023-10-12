package com.sitrack.sitrackbackend.domain;

import com.sitrack.sitrackbackend.domain.account.UserAccount;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
public class Purchase extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long id;

    @Setter
    @JoinColumn(name = "user_Id")
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    @Setter
    @JoinColumn(name = "product_id")
    @ManyToOne(optional = false)
    private Product product;

    @Setter
    @Column(nullable = false)
    private Long totalAmount; // 총 주문 금액

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime purchaseDate; // 주문 일자

    @Setter
    @Column(nullable = false)
    private String purchaseAddress; // 주문배송 주소

    @Setter
    @Column(nullable = false)
    private String purchaseState; // 주문 현황

    @Setter
    @Column(nullable = true)
    private String purchaseRequest; // 요청 사항

    public Purchase(Long id, UserAccount userAccount, Product product, Long totalAmount, LocalDateTime purchaseDate, String purchaseAddress, String purchaseState, String purchaseRequest) {
        this.id = id;
        this.userAccount = userAccount;
        this.product = product;
        this.totalAmount = totalAmount;
        this.purchaseDate = purchaseDate;
        this.purchaseAddress = purchaseAddress;
        this.purchaseState = purchaseState;
        this.purchaseRequest = purchaseRequest;
    }

    protected Purchase() {

    }

    public static Purchase of(Long id, UserAccount userAccount, Product product, Long totalAmount, LocalDateTime purchaseDate, String purchaseAddress, String purchaseState, String purchaseRequest){
        return new Purchase(id, userAccount, product, totalAmount, purchaseDate, purchaseAddress, purchaseState, purchaseRequest);
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
