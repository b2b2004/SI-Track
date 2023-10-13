package com.sitrack.sitrackbackend.dto;

public record LoginDto(
        String userId,
        String userPassword
) {
    public static LoginDto of(String userId, String userPassword) {
        return new LoginDto(userId, userPassword);
    }
}
