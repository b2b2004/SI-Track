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
public class Order extends AuditingFields{

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
    private Long totalAmount; // 총 주문 금액

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime orderDate; // 주문 일자

    @Setter
    @Column(nullable = false)
    private String orderAddress; // 주문배송 주소

    @Setter
    @Column(nullable = false)
    private String orderState; // 주문 현황

    @Setter
    @Column(nullable = true)
    private String orderRequest; // 요청 사항

    public Order(Long id, UserAccount userAccount, Product productId, Long totalAmount, LocalDateTime orderDate, String orderAddress, String orderState, String orderRequest) {
        this.id = id;
        this.userAccount = userAccount;
        this.productId = productId;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.orderAddress = orderAddress;
        this.orderState = orderState;
        this.orderRequest = orderRequest;
    }

    public Order of(Long id, UserAccount userAccount, Product productId, Long totalAmount, LocalDateTime orderDate, String orderAddress, String orderState, String orderRequest){
        return new Order(id,userAccount,productId,totalAmount, orderDate, orderAddress, orderState, orderRequest);
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
