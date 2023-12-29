package com.sitrack.sitrackbackend.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitrack.sitrackbackend.config.security.JwtProvider;
import com.sitrack.sitrackbackend.config.security.auth.PrincipalDetails;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;


    // 인증 객체(Authentication)을 만들기 시도
    // attemptAuthentication 추상메소드의 구현은 상속한 UsernamePasswordAuthenticationFilter에 구현 되어 있습니다.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {

            ObjectMapper om = new ObjectMapper();
            UserAccount user = om.readValue(request.getInputStream(), UserAccount.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user.getUserId(),
                    user.getUserPassword()
            );

            /*
            - Filter를 통해 AuthenticationToken을 AuthenticationManager에 위임한다.
               UsernamePasswordAuthenticationToken 오브젝트가 생성된 후, AuthenticationManager의 인증 메소드를 호출한다.
            - PrincipalDetailsService의 loadUserByUsername() 함수가 실행된다.
            => 정상이면 authentication이 반환된다.
            * */
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            /*
            - authentication 객체가 session 영역에 정보를 저장한다. -> 로그인 처리
            - authenticatino 객체가 세션에 저장한 '방식'을 반환한다.
            - 참고 : security가 권한을 관리해주기 때문에 굳이 JWT에서는 세션을 만들필요는 없지만, 권한 처리를 위해 session을 사용한다.
             */

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("Authentication 확인: "+principalDetails.getUser().getUserName());

            return  authentication;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("로그인 실패 - 아이디와 비밀번호를 확인해주세요.");
        }
    }


    /*

     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        String userId = principalDetails.getUser().getUserId();
        String userPassword = principalDetails.getUser().getUserPassword();

        String jwtToken = jwtProvider.generateJwtToken(userId, userPassword);
        String refreshToken = jwtProvider.createRefreshToken(userId, userPassword);

        // 쿠키에 Refresh Token 추가
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);

        response.getWriter().write("로그인 성공");
        response.addCookie(refreshTokenCookie);
        response.addHeader("Authorization", "Bearer " + jwtToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        JSONObject responseJson = new JSONObject();
        try {
            responseJson.put("status", HttpStatus.UNAUTHORIZED);
            responseJson.put("message", failed.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.getWriter().print(responseJson);
    }


}