package com.sitrack.sitrackbackend.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sitrack.sitrackbackend.Exception.ErrorCode;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
public class JwtProvider {

    private final UserAccountRepository userAccountRepository;

    static Long ACCESS_TOKEN_EXPIRE_TIME = 60L * 60L * 1000L; // 만료 시간 1시간

    static Long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60L * 60L * 24L * 3L; // 만료 시간 3일

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.refresh_secret}")
    private String refresh_secret;

    private Algorithm getSign(String key){
        return Algorithm.HMAC512(key);
    }

    //객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        this.secretKey = Base64.getEncoder().encodeToString(this.secretKey.getBytes());
    }

    // Jwt 토큰 생성
    public String generateJwtToken(String userId, String userPassword){

        Date tokenExpiration = new Date(System.currentTimeMillis() + (ACCESS_TOKEN_EXPIRE_TIME));

        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(tokenExpiration)
                .withClaim("userId", userId)
                .withClaim("userPassword", userPassword)
                .sign(this.getSign(secretKey));
    }

    /**
     *  RefreshToken 생성
     */
    public String createRefreshToken(String userId, String userPassword) {

        Date tokenExpiration = new Date(System.currentTimeMillis() + (REFRESH_TOKEN_EXPIRE_TIME));

        return JWT.create()
                .withSubject("refreshToken")
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(tokenExpiration)
                .withClaim("userId", userId)
                .withClaim("userPassword", userPassword)
                .sign(this.getSign(refresh_secret));
    }

    public String getUserId(String jwtToken){
        return JWT.require(this.getSign(secretKey)).build().verify(jwtToken).getClaim("userId").asString();
    }

    public String getRefreshUserId(String jwtToken){
        return JWT.require(this.getSign(refresh_secret)).build().verify(jwtToken).getClaim("userId").asString();
    }

    public String getRefreshUserPassword(String jwtToken){
        return JWT.require(this.getSign(refresh_secret)).build().verify(jwtToken).getClaim("userPassword").asString();
    }

    public boolean validateToken(String token) {
        try {
            // 토큰 유효성 검증
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
            return true;
        }catch (SecurityException | MalformedJwtException e) {
            throw new JwtException(ErrorCode.WRONG_TYPE_TOKEN.getMessage());
        } catch (ExpiredJwtException e) {
            throw new JwtException(ErrorCode.EXPIRED_TOKEN.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new JwtException(ErrorCode.UNSUPPORTED_TOKEN.getMessage());
        } catch (IllegalArgumentException e) {
            throw new JwtException(ErrorCode.INVALID_TOKEN.getMessage());
        } catch (Exception e){
            throw new JwtException(ErrorCode.UNKNOWN_ERROR.getMessage());
        }
    }

    public UserAccount getUser(String jwtToken){
        try {
            String userId = JWT.require(this.getSign(secretKey)).build().verify(jwtToken).getClaim("userId").asString();
            if (userId == null){
                return null;
            }
            UserAccount tokenUser = userAccountRepository.findByUserId(userId)
                    .orElseThrow(()-> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + userId));
            return tokenUser;

        } catch (Exception e){
            e.printStackTrace();
            throw new JwtException("Token getUser Error");
        }
    }
}
