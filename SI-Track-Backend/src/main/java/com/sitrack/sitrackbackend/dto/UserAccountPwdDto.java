package com.sitrack.sitrackbackend.dto;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record UserAccountPwdDto(
        @NotBlank(message = "현재 비밀번호를 입력 해주세요.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        String nowPwd,

        @NotBlank(message = "변경 비밀번호를 입력 해주세요.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        @NotNull String newPwd
) {
    public static UserAccountPwdDto of(String nowPwd, String newPwd){
        return new UserAccountPwdDto(nowPwd, newPwd);
    }
}
