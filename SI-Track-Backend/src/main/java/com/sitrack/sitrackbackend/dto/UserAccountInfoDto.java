package com.sitrack.sitrackbackend.dto;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record UserAccountInfoDto(
        @NotBlank(message = "이메일을 입력 해주세요.")
        @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
        String userEmail,

        @NotBlank(message = "핸드폰 번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",message = "핸드폰 번호의 형식이 올바르지 않습니다.")
        String userPhoneNumber
) {
    public static UserAccountInfoDto of(String userEmail, String userPhoneNumber){
        return new UserAccountInfoDto(userEmail, userPhoneNumber);
    }
}
