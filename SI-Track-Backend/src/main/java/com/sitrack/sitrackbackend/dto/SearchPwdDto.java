package com.sitrack.sitrackbackend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record SearchPwdDto(

        @NotBlank(message = "아이디를 입력 해주세요.")
        String userId,

        @NotBlank(message = "이메일을 입력 해주세요.")
        @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
        String userEmail
) {
    public static SearchPwdDto of(String userId, String userEmail){
        return new SearchPwdDto(userId, userEmail);
    }
}
