package com.sitrack.sitrackbackend.config.security.filter;


import com.sitrack.sitrackbackend.Exception.ErrorCode;
import com.sitrack.sitrackbackend.config.security.JwtProvider;
import com.sitrack.sitrackbackend.config.security.auth.PrincipalDetails;
import com.sitrack.sitrackbackend.config.security.auth.PrincipalDetailsService;
import com.sitrack.sitrackbackend.domain.account.UserAccount;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtProvider jwtProvider;
    private PrincipalDetailsService principalDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider, PrincipalDetailsService principalDetailsService ) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
        this.principalDetailsService = principalDetailsService;
    }

    // 인증이나 권한이 필요한 주소 요청이 있을 때 해당 필터를 통과한다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwtHeader = request.getHeader("Authorization");

        // header가 있는지 확인
        try{
            if (jwtHeader == null || !jwtHeader.startsWith("Bearer ")) {
                chain.doFilter(request, response);
                return;
            }
        }catch (NullPointerException e){
            throw new NullPointerException("토큰이 존재하지 않습니다.");
        }

        // JWT 토큰을 검증하여 정상적인 사용자인지 확인
        String jwtToken = request.getHeader("Authorization").replace("Bearer ","");
        if(jwtProvider.validateToken(jwtToken)){
            UserAccount tokenUser = jwtProvider.getUser(jwtToken);
            if(tokenUser != null){
                PrincipalDetails principalDetails = new PrincipalDetails(tokenUser);

                // JWT 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다.
                Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

                // 시큐리티 세션에 Authentcation 을 저장한다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}