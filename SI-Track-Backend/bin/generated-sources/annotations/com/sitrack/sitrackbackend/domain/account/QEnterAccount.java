package com.sitrack.sitrackbackend.domain.account;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEnterAccount is a Querydsl query type for EnterAccount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEnterAccount extends EntityPathBase<EnterAccount> {

    private static final long serialVersionUID = -54497129L;

    public static final QEnterAccount enterAccount = new QEnterAccount("enterAccount");

    public final com.sitrack.sitrackbackend.domain.QAuditingFields _super = new com.sitrack.sitrackbackend.domain.QAuditingFields(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final StringPath enterAccountEmail = createString("enterAccountEmail");

    public final StringPath enterAccountId = createString("enterAccountId");

    public final StringPath enterAccountName = createString("enterAccountName");

    public final StringPath enterAccountPassword = createString("enterAccountPassword");

    public final StringPath enterAccountPhoneNumber = createString("enterAccountPhoneNumber");

    public final StringPath enterAddress = createString("enterAddress");

    public final NumberPath<Long> enterAuthKey = createNumber("enterAuthKey", Long.class);

    public final StringPath enterCeo = createString("enterCeo");

    public final StringPath enterName = createString("enterName");

    public final StringPath enterNumber = createString("enterNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public QEnterAccount(String variable) {
        super(EnterAccount.class, forVariable(variable));
    }

    public QEnterAccount(Path<? extends EnterAccount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEnterAccount(PathMetadata metadata) {
        super(EnterAccount.class, metadata);
    }

}

