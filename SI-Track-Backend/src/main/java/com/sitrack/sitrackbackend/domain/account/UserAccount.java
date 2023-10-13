package com.sitrack.sitrackbackend.domain.account;


import com.sitrack.sitrackbackend.domain.AuditingFields;
import com.sitrack.sitrackbackend.domain.Cart;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
public class UserAccount extends AuditingFields {

    @Id
    @Column(length = 100, nullable = false, name = "user_id")
    private String userId;

    @Setter
    @Column(nullable = false)
    private String userPassword;

    @Setter
    @Column(length = 100, nullable = false)
    private String userName;

    @Setter
    @Column(length = 100, nullable = false)
    private String userEmail;

    @Setter
    @Column(length = 100, nullable = false)
    private String userPhoneNumber;

    protected UserAccount(){}

    public UserAccount(String userId, String userPassword, String userName, String userEmail, String userPhoneNumber) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
    }

    public static UserAccount of(String userId, String userPassword, String userName, String userEmail, String userPhoneNumber){
        return new UserAccount(userId, userPassword, userName, userEmail, userPhoneNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount that)) return false;
        return this.getUserId() != null && this.getUserId().equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId());
    }
}
