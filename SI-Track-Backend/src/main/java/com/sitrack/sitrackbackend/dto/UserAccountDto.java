package com.sitrack.sitrackbackend.dto;

import com.sitrack.sitrackbackend.domain.account.UserAccount;

import java.time.LocalDateTime;

public record UserAccountDto(
    String userId,
    String userPassword,
    String userName,
    String userEmail,
    String userPhoneNumber,
    LocalDateTime createdAt,
    String createdBy,
    LocalDateTime modifiedAt,
    String modifiedBy
) {

    public UserAccountDto of(String userId, String userPassword, String userName, String userEmail, String userPhoneNumber) {
        return new UserAccountDto(userId, userPassword, userName, userEmail, userPhoneNumber, null, null, null, null);
    }

    public UserAccountDto of(String userId, String userPassword, String userName, String userEmail, String userPhoneNumber, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new UserAccountDto(userId, userPassword, userName, userEmail, userPhoneNumber, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static UserAccountDto from(UserAccount entity){
        return new UserAccountDto(
                entity.getUserId(),
                entity.getUserPassword(),
                entity.getUserName(),
                entity.getUserEmail(),
                entity.getUserPhoneNumber(),
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
