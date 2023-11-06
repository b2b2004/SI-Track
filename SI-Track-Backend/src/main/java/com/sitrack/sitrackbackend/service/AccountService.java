package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.config.security.JwtProvider;
import com.sitrack.sitrackbackend.config.security.auth.PrincipalDetails;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.LoginDto;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserAccountRepository userAccountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    public String signup(UserAccountDto userAccountDto){
        String rawPassword = userAccountDto.userPassword();
        UserAccount userAccount = userAccountDto.toEntity();
        userAccount.setUserPassword(bCryptPasswordEncoder.encode(rawPassword));
        userAccountRepository.save(userAccount);
        return "회원 가입 완료";
    }

    public String login(LoginDto loginDto){
        UserAccount user = userAccountRepository.findByUserId(loginDto.userId())
                .orElseThrow(()-> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + loginDto.userId()));
        String rawPassword = loginDto.userPassword();

        String userId = user.getUserId();
        String userPassword = user.getUserPassword();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, userPassword);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증이 완료된 객체이면,
        if(authentication.isAuthenticated()) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            String authenticatedUserID = principalDetails.getUser().getUserId();
            String authenticateduserPassword = principalDetails.getUser().getUserPassword();
            return "로그인 성공 " + jwtProvider.generateJwtToken( authenticatedUserID, authenticateduserPassword);
        }

        return "로그인 실패";
    }

}
