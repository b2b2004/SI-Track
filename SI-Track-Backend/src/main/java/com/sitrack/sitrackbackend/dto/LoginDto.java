package com.sitrack.sitrackbackend.dto;

import javax.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank(message = "아이디를 입력 해주세요.") String userId,
        @NotBlank(message = "비밀번호를 입력 해주세요.") String userPassword
) {
    public static LoginDto of(String userId, String userPassword) {
        return new LoginDto(userId, userPassword);
    }
}
