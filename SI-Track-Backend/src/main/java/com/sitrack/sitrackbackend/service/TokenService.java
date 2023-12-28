package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.Exception.CustomException;
import com.sitrack.sitrackbackend.config.security.JwtProvider;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.domain.redis.RefreshToken;
import com.sitrack.sitrackbackend.repository.RefreshTokenRepository;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.sitrack.sitrackbackend.Exception.ErrorCode.REFRESH_TOKEN_NOT_FOUND;
import static com.sitrack.sitrackbackend.Exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserAccountRepository userAccountRepository;
    private final JwtProvider jwtProvider;

    public String validatateRefreshToken(String authentication) {
        String[] splitJwt = authentication.split(" ");
        String search = splitJwt[1];

        RefreshToken refreshToken = refreshTokenRepository.findByAccessToken(search)
                .orElseThrow(()-> new CustomException(REFRESH_TOKEN_NOT_FOUND));
        UserAccount user = userAccountRepository.findByUserId(refreshToken.getId())
                .orElseThrow(()-> new CustomException(USER_NOT_FOUND));
        String newAccessToken = jwtProvider.generateJwtToken(user.getUserId(), user.getUserPassword());
        RefreshTokenSave(refreshToken, newAccessToken);
       return newAccessToken;
    }

    public void RefreshTokenSave(RefreshToken refreshToken, String newAccessToken){
        refreshTokenRepository.save(new RefreshToken(refreshToken.getId(), refreshToken.getRefreshToken(), newAccessToken));
    }
}
