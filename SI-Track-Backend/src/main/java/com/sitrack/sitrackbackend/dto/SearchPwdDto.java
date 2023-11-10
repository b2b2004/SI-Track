package com.sitrack.sitrackbackend.dto;

import com.sun.istack.NotNull;

public record SearchPwdDto(
        @NotNull String userId,
        @NotNull String userEmail
) {
    public static SearchPwdDto of(String userId, String userEmail){
        return new SearchPwdDto(userId, userEmail);
    }
}
