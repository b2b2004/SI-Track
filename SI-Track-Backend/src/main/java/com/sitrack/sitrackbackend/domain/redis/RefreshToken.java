package com.sitrack.sitrackbackend.domain.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@AllArgsConstructor
@Getter
@RedisHash(value = "token", timeToLive = 60*60*24*3) // 3일
public class RefreshToken {

    @Id
    private String id;

    private String refreshToken;

    @Indexed
    private String accessToken;
}
