package com.sitrack.sitrackbackend.controller;

import com.sitrack.sitrackbackend.config.security.auth.PrincipalDetails;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.*;
import com.sitrack.sitrackbackend.dto.request.TokenReqeust;
import com.sitrack.sitrackbackend.dto.response.SearchIdResponse;
import com.sitrack.sitrackbackend.service.AccountService;
import com.sitrack.sitrackbackend.service.EmailService;
import com.sitrack.sitrackbackend.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RequiredArgsConstructor
@RequestMapping("/account")
@Controller
public class AccountController {

    private final AccountService accountService;
    private final TokenService tokenService;

//    @PostMapping("/login")
//    public ResponseEntity<?> userAccountLogin(@Valid @RequestBody LoginDto loginDto){
//        return new ResponseEntity<>(accountService.login(loginDto), HttpStatus.OK);
//    }

    // 회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<?> userAccountSignup(@Valid @RequestBody UserAccountDto userAccountDto){
        String msg = accountService.signup(userAccountDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // 아이디 찾기
    @PostMapping("/user/searchId")
    public ResponseEntity<?> userSearchId(@Valid @RequestBody SearchIdDto searchIdDto){
        SearchIdResponse searchIdResponse = accountService.searchId(searchIdDto);
        return new ResponseEntity<>(searchIdResponse, HttpStatus.OK);
    }

    // 비밀번호 찾기
    // 이메일 임시 비밀번호 전송
    @PostMapping("/user/searchPwd")
    public ResponseEntity<?> userSearchPwd(@Valid @RequestBody SearchPwdDto searchPwdDto){
        String msg = accountService.searchPwd(searchPwdDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // 비밀번호 변경
    @PostMapping("/login/changePwd")
    public ResponseEntity<?> changePwd(@Valid @RequestBody UserAccountPwdDto userAccountPwdDto,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails){
        UserAccount user = principalDetails.getUser();
        String msg = accountService.chagePwd(userAccountPwdDto, user);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // 개인정보 변경
    @PostMapping("/login/changeInfo")
    public ResponseEntity<?> changeInfo(@Valid @RequestBody UserAccountInfoDto userAccountInfoDto,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails){
        UserAccount user = principalDetails.getUser();
        String msg = accountService.chageInformaition(userAccountInfoDto, user);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping("/login/info")
    public ResponseEntity<?> findInfo(@AuthenticationPrincipal PrincipalDetails principalDetails){
        UserAccount user = principalDetails.getUser();
        UserAccountDto user1 = accountService.findUser(user);
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }

    /**
     *  RequestHeader로 받을 시 Spring Security Filter에 걸려
     *  ExpiredJwtException 오류가 나서 RequestBody로 진행..
     */
    @GetMapping("/user/reissue/token")
    public ResponseEntity<?> reissueToken(@RequestBody TokenReqeust tokenReqeust){
        String newToken = tokenService.validatateRefreshToken(tokenReqeust.authentication());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + newToken);
        return new ResponseEntity<>("토큰 발급 성공" ,headers ,HttpStatus.OK);
    }
}
