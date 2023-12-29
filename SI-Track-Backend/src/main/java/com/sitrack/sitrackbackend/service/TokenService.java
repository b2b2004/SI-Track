package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.Exception.CustomException;
import com.sitrack.sitrackbackend.Exception.ErrorCode;
import com.sitrack.sitrackbackend.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtProvider jwtProvider;

    public String validateRefreshToken(String refreshToken) {
        if(refreshToken == null || refreshToken.isEmpty()){
            throw new CustomException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }
        String userId = jwtProvider.getRefreshUserId(refreshToken);
        String userPassword = jwtProvider.getRefreshUserPassword(refreshToken);
        String reissueToken = jwtProvider.generateJwtToken(userId, userPassword);
        return reissueToken;
    }

}
