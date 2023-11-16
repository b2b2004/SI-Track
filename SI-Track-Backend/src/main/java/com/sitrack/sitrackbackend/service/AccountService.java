package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.config.security.JwtProvider;
import com.sitrack.sitrackbackend.config.security.auth.PrincipalDetails;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.domain.constant.RoleType;
import com.sitrack.sitrackbackend.dto.*;
import com.sitrack.sitrackbackend.dto.response.SearchIdResponse;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserAccountRepository userAccountRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    public String signup(UserAccountDto userAccountDto) {
        String rawPassword = userAccountDto.userPassword();
        UserAccount userAccount = userAccountDto.toEntity();
        userAccount.setUserPassword(bCryptPasswordEncoder.encode(rawPassword));
        userAccount.setRoleType(RoleType.USER);
        userAccountRepository.save(userAccount);
        return "회원 가입 완료";
    }

    public String login(LoginDto loginDto) {
        UserAccount user = userAccountRepository.findByUserId(loginDto.userId())
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + loginDto.userId()));
        String rawPassword = loginDto.userPassword();

        String userId = user.getUserId();
        String userPassword = user.getUserPassword();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, userPassword);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증이 완료된 객체이면,
        if (authentication.isAuthenticated()) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            String authenticatedUserID = principalDetails.getUser().getUserId();
            String authenticateduserPassword = principalDetails.getUser().getUserPassword();
            return "로그인 성공 " + jwtProvider.generateJwtToken(authenticatedUserID, authenticateduserPassword);
        }

        return "로그인 실패";
    }

    public SearchIdResponse searchId(SearchIdDto searchIdDto) {
        UserAccount user = userAccountRepository.findByUserNameAndUserEmail(searchIdDto.userName(), searchIdDto.userEmail())
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자의 아이디를 찾을 수 없습니다. : " + searchIdDto.userName()));
        SearchIdResponse searchIdResponse = SearchIdResponse.of(user.getUserId(), user.getCreatedAt());
        return searchIdResponse;
    }

    public String searchPwd(SearchPwdDto searchPwdDto) {
        UserAccount user = userAccountRepository.findByUserIdAndUserEmail(searchPwdDto.userId(), searchPwdDto.userEmail())
                .orElseThrow(() -> new UsernameNotFoundException("해당 정보의 사용자가 없습니다. : " + searchPwdDto.userId()));
        String newPWD = emailService.createMailAndcreatePWD(user.getUserName(), user.getUserEmail());
        user.setUserPassword(bCryptPasswordEncoder.encode(newPWD));
        return "이메일로 임시 비밀 번호가 발송 되었습니다.";
    }

    public String chagePwd(UserAccountPwdDto userAccountPwdDto, UserAccount user1) {
        UserAccount user = userAccountRepository.findByUserId(user1.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("해당 정보의 사용자가 없습니다. : " + user1.getUserId()));
        if(!userAccountPwdDto.nowPwd().equals(user.getUserPassword())){
            return "현재 비밀번호와 일치하지 않습니다.";
        }
        user.setUserPassword(bCryptPasswordEncoder.encode(userAccountPwdDto.newPwd()));
        return "비밀번호가 변경 되었습니다.";
    }

    public String chageInformaition(UserAccountInfoDto userAccountInfoDto, UserAccount user1) {
        UserAccount user = userAccountRepository.findByUserId(user1.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("해당 정보의 사용자가 없습니다. : " + user1.getUserId()));
        user.setUserEmail(userAccountInfoDto.userEmail());
        user.setUserPhoneNumber(userAccountInfoDto.userPhoneNumber());

        return "정보가 변경 되었습니다.";
    }
}

