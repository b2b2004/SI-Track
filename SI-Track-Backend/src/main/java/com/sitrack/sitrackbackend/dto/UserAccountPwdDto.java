package com.sitrack.sitrackbackend.dto;

import com.sun.istack.NotNull;

public record UserAccountPwdDto(
        @NotNull String nowPwd,
        @NotNull String newPwd
) {
    public static UserAccountPwdDto of(String nowPwd, String newPwd){
        return new UserAccountPwdDto(nowPwd, newPwd);
    }
}
