package com.sitrack.sitrackbackend.dto;

import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.domain.constant.RoleType;

import java.time.LocalDateTime;

public record UserAccountDto(
    String userId,
    String userPassword,
    String userName,
    String userEmail,
    String userPhoneNumber,
    RoleType roleType,
    LocalDateTime createdAt,
    String createdBy,
    LocalDateTime modifiedAt,
    String modifiedBy
) {

    public static UserAccountDto of(String userId, String userPassword, String userName, String userEmail, String userPhoneNumber, RoleType roleType) {
        return new UserAccountDto(userId, userPassword, userName, userEmail, userPhoneNumber, roleType, null, null, null, null);
    }

    public static UserAccountDto of(String userId, String userPassword, String userName, String userEmail, String userPhoneNumber, RoleType roleType, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new UserAccountDto(userId, userPassword, userName, userEmail, userPhoneNumber, roleType, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static UserAccountDto from(UserAccount entity){
        return new UserAccountDto(
                entity.getUserId(),
                entity.getUserPassword(),
                entity.getUserName(),
                entity.getUserEmail(),
                entity.getUserPhoneNumber(),
                entity.getRoleType(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public UserAccount toEntity(){
        return UserAccount.of(
                userId,
                userPassword,
                userName,
                userEmail,
                userPhoneNumber
        );
    }

}
