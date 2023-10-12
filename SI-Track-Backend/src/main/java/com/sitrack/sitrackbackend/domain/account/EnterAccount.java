package com.sitrack.sitrackbackend.domain.account;


import com.sitrack.sitrackbackend.domain.AuditingFields;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
public class EnterAccount extends AuditingFields {

    @Id
    @Column(length = 100, nullable = false)
    private String enterAccountId;

    @Setter
    @Column(nullable = false)
    private String enterAccountPassword;

    @Setter
    @Column(length = 100, nullable = false)
    private String enterAccountName;

    @Setter
    @Column(length = 100, nullable = false)
    private String enterAccountEmail;

    @Setter
    @Column(length = 100, nullable = false)
    private String enterAccountPhoneNumber;

    @Setter
    @Column(length = 100, nullable = false)
    private String enterName;

    @Setter
    @Column(length = 100, nullable = false)
    private String enterNumber;

    @Setter
    @Column(length = 100, nullable = false)
    private String enterAddress;

    @Setter
    @Column(length = 100, nullable = false)
    private String enterCeo;

    @Setter
    private Long enterAuthKey;

    protected EnterAccount() {}

    public EnterAccount(String enterAccountId, String enterAccountPassword, String enterAccountName, String enterAccountEmail, String enterAccountPhoneNumber, String enterName, String enterNumber, String enterAddress, String enterCeo, Long enterAuthKey) {
        this.enterAccountId = enterAccountId;
        this.enterAccountPassword = enterAccountPassword;
        this.enterAccountName = enterAccountName;
        this.enterAccountEmail = enterAccountEmail;
        this.enterAccountPhoneNumber = enterAccountPhoneNumber;
        this.enterName = enterName;
        this.enterNumber = enterNumber;
        this.enterAddress = enterAddress;
        this.enterCeo = enterCeo;
        this.enterAuthKey = enterAuthKey;
    }

    public EnterAccount of(String enterAccountId, String enterAccountPassword, String enterAccountName, String enterAccountEmail, String enterAccountPhoneNumber, String enterName, String enterNumber, String enterAddress, String enterCeo, Long enterAuthKey){
        return new EnterAccount(enterAccountId, enterAccountPassword, enterAccountName, enterAccountEmail, enterAccountPhoneNumber, enterName, enterNumber, enterAddress, enterCeo, enterAuthKey);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnterAccount that)) return false;
        return this.getEnterAccountId() != null && this.getEnterAccountId().equals(that.getEnterAccountId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getEnterAccountId());
    }
}
