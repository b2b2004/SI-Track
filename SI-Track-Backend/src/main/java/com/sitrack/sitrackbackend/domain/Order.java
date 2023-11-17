package com.sitrack.sitrackbackend.domain;

import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.request.OrderRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Table(name = "orders")
@ToString(callSuper = true)
@Entity
public class Order extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Setter
    @Column(nullable = false)
    private Long totalAmount; // 총 주문 금액

    @Setter
    @Column(nullable = false)
    private String orderAddress; // 주문배송 주소

    @Setter
    @Column(nullable = false)
    private String orderStatus; // 주문 현황

    @Setter
    @Column(nullable = true)
    private String orderRequest; // 요청 사항

    @Setter
    @Column(nullable = false)
    private String recipient; // 수령인

    @Setter
    @Column(nullable = false)
    private String phoneNumber; // 수령인 핸드폰 번호


    /*연관 관계 메서드*/
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        userAccount.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    protected Order() {}

    public Order(UserAccount userAccount, Long totalAmount, String orderAddress, String orderStatus, String orderRequest, String recipient, String phoneNumber, List<OrderItem> orderItems) {
        this.userAccount = userAccount;
        this.totalAmount = totalAmount;
        this.orderAddress = orderAddress;
        this.orderStatus = orderStatus;
        this.orderRequest = orderRequest;
        this.recipient = recipient;
        this.phoneNumber = phoneNumber;
        for(OrderItem orderItem : orderItems){
            addOrderItem(orderItem);
        }
    }

    public static Order of(UserAccount userAccount, Long totalAmount, String orderAddress, String orderStatus, String orderRequest, String recipient, String phoneNumber, List<OrderItem> orderItem){
        return new Order(userAccount, totalAmount, orderAddress, orderStatus, orderRequest, recipient, phoneNumber, orderItem);
    }

    public static Order of(UserAccount userAccount, OrderRequest orderRequest, List<OrderItem> orderItems){
        return new Order(userAccount, orderRequest.totalAmount(), orderRequest.orderAddress(), "주문 대기", orderRequest.orderRequest(), orderRequest.recipient(), orderRequest.phoneNumber(), orderItems);
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
