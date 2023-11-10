package com.sitrack.sitrackbackend.dto;

import com.sun.istack.NotNull;

public record UserAccountInfoDto(
        @NotNull String userEmail,
        @NotNull String userPhoneNumber
) {
    public static UserAccountInfoDto of(String userEmail, String userPhoneNumber){
        return new UserAccountInfoDto(userEmail, userPhoneNumber);
    }
}
