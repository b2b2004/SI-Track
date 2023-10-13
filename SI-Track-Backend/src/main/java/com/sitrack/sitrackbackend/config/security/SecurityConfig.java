package com.sitrack.sitrackbackend.config.security;

import com.sitrack.sitrackbackend.config.security.auth.PrincipalDetailsService;
import com.sitrack.sitrackbackend.config.security.filter.JwtAuthenticationFilter;
import com.sitrack.sitrackbackend.config.security.filter.JwtAuthorizationFilter;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final PrincipalDetailsService principalDetailsService;
    private final UserAccountRepository userAccountRepository;

    // 시큐리티 필터 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable() // http의 기본 인증. ID, PW 인증방식
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider()))  // AuthenticationManager
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),  jwtTokenProvider(), principalDetailsService))  // AuthenticationManager
                .authorizeHttpRequests()
                .anyRequest().permitAll();

        return http.build();
    }

    @Bean
    public JwtProvider jwtTokenProvider() {
        return new JwtProvider(userAccountRepository);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
