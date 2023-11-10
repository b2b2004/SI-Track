package com.sitrack.sitrackbackend.dto.response;

import java.time.LocalDateTime;

public record SearchIdResponse(
        String userId,
        LocalDateTime createdAt
) {

    public static SearchIdResponse of(String userId, LocalDateTime createdAt){
        return new SearchIdResponse(userId, createdAt);
    }
}
