package com.sitrack.sitrackbackend.config.security;

import com.sitrack.sitrackbackend.config.CorsConfig;
import com.sitrack.sitrackbackend.config.security.auth.PrincipalDetailsService;
import com.sitrack.sitrackbackend.config.security.filter.JwtAuthenticationFilter;
import com.sitrack.sitrackbackend.config.security.filter.JwtAuthorizationFilter;
import com.sitrack.sitrackbackend.config.security.filter.JwtExceptionFilter;
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
    private final JwtExceptionFilter jwtExceptionFilter;

    private final CorsConfig corsConfig;

    // 시큐리티 필터 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .antMatchers("/account/user/**", "/product/**", "/product/list").permitAll()
                .antMatchers("/product/admin/**", "/admin/**").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/manager/**").hasAnyRole("MANAGER")
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .httpBasic().disable() // http의 기본 인증. ID, PW 인증방식
                .addFilter(corsConfig.corsFilter())
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider()))  // AuthenticationManager
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),  jwtTokenProvider(), principalDetailsService))
                .addFilterBefore(jwtExceptionFilter, JwtAuthorizationFilter.class);

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
