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
import javax.websocket.DecodeException;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
public class JwtProvider {

    private final UserAccountRepository userAccountRepository;

    static Long EXPIRE_TIME = 60L * 60L * 1000L; // 만료 시간 1시간

    @Value("${jwt.secret}")
    private String secretKey;

    private Algorithm getSign(){
        return Algorithm.HMAC512(secretKey);
    }

    //객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        this.secretKey = Base64.getEncoder().encodeToString(this.secretKey.getBytes());
    }

    // Jwt 토큰 생성
    public String generateJwtToken(String userId, String userPassword){

        Date tokenExpiration = new Date(System.currentTimeMillis() + (EXPIRE_TIME));

        String jwtToken = JWT.create()
                .withSubject(userId) //토큰 이름
                .withExpiresAt(tokenExpiration)
                .withClaim("userId", userId)
                .withClaim("userPassword", userPassword)
                .sign(this.getSign());

        return jwtToken;
    }

    /**
     * 토큰 검증
     *  - 토큰에서 가져온 userId 정보와 DB의 유저 정보 일치하는지 확인
     *  - 토큰 만료 시간이 지났는지 확인
     * @param jwtToken
     * @return 유저 객체 반환
     */

    public String getUserId(String jwtToken){
        return JWT.require(this.getSign()).build().verify(jwtToken).getClaim("userId").asString();
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
            String userId = JWT.require(this.getSign()).build().verify(jwtToken).getClaim("userId").asString();
            // 비어있는 값이다.
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
