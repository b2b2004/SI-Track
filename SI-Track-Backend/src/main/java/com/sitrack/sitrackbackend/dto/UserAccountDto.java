package com.sitrack.sitrackbackend.dto;

import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.domain.constant.RoleType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

public record UserAccountDto(
        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        @Pattern(regexp = "^[a-z0-9]{6,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 6~20자리여야 합니다.")
        String userId,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        String userPassword,

        @NotBlank(message = "이름은 필수 입력 값입니다.")
        String userName,

        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
        String userEmail,

        @NotBlank(message = "핸드폰 번호는 필수 입력 값입니다.")
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
