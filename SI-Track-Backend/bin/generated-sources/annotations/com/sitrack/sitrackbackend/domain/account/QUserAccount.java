package com.sitrack.sitrackbackend.domain.account;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAccount is a Querydsl query type for UserAccount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAccount extends EntityPathBase<UserAccount> {

    private static final long serialVersionUID = -313548992L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserAccount userAccount = new QUserAccount("userAccount");

    public final com.sitrack.sitrackbackend.domain.QAuditingFields _super = new com.sitrack.sitrackbackend.domain.QAuditingFields(this);

    public final com.sitrack.sitrackbackend.domain.QCart cart;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final ListPath<com.sitrack.sitrackbackend.domain.Order, com.sitrack.sitrackbackend.domain.QOrder> orders = this.<com.sitrack.sitrackbackend.domain.Order, com.sitrack.sitrackbackend.domain.QOrder>createList("orders", com.sitrack.sitrackbackend.domain.Order.class, com.sitrack.sitrackbackend.domain.QOrder.class, PathInits.DIRECT2);

    public final EnumPath<com.sitrack.sitrackbackend.domain.constant.RoleType> roleType = createEnum("roleType", com.sitrack.sitrackbackend.domain.constant.RoleType.class);

    public final StringPath userEmail = createString("userEmail");

    public final StringPath userId = createString("userId");

    public final StringPath userName = createString("userName");

    public final StringPath userPassword = createString("userPassword");

    public final StringPath userPhoneNumber = createString("userPhoneNumber");

    public QUserAccount(String variable) {
        this(UserAccount.class, forVariable(variable), INITS);
    }

    public QUserAccount(Path<? extends UserAccount> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserAccount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserAccount(PathMetadata metadata, PathInits inits) {
        this(UserAccount.class, metadata, inits);
    }

    public QUserAccount(Class<? extends UserAccount> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cart = inits.isInitialized("cart") ? new com.sitrack.sitrackbackend.domain.QCart(forProperty("cart"), inits.get("cart")) : null;
    }

}

