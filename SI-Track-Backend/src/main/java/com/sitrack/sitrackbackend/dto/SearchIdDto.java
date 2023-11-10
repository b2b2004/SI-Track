package com.sitrack.sitrackbackend.dto;

import com.sun.istack.NotNull;

public record SearchIdDto(
        @NotNull String userName,
        @NotNull String userEmail
) {
    public static SearchIdDto of(String userName, String userEmail){
        return new SearchIdDto(userName, userEmail);
    }
}
