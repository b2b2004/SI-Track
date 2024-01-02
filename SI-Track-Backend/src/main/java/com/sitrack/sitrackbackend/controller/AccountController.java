package com.sitrack.sitrackbackend.controller;

import com.sitrack.sitrackbackend.config.security.auth.PrincipalDetails;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.*;
import com.sitrack.sitrackbackend.dto.response.SearchIdResponse;
import com.sitrack.sitrackbackend.service.AccountService;
import com.sitrack.sitrackbackend.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/account")
@Controller
@Api(tags = "User Controller")
public class AccountController {

    private final AccountService accountService;
    private final TokenService tokenService;

//    @PostMapping("/login")
//    public ResponseEntity<?> userAccountLogin(@Valid @RequestBody LoginDto loginDto){
//        return new ResponseEntity<>(accountService.login(loginDto), HttpStatus.OK);
//    }

    // 회원가입
    @ApiOperation(value = "유저 회원가입", notes = "유저 회원가입 Method")
    @PostMapping("/user/signup")
    public ResponseEntity<?> userAccountSignup(@Valid @RequestBody UserAccountDto userAccountDto){
        String msg = accountService.signup(userAccountDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // 아이디 찾기
    @ApiOperation(value = "유저 아이디 찾기", notes = "Name, Email을 통한 아이디 찾기")
    @PostMapping("/user/searchId")
    public ResponseEntity<?> userSearchId(@Valid @RequestBody SearchIdDto searchIdDto){
        SearchIdResponse searchIdResponse = accountService.searchId(searchIdDto);
        return new ResponseEntity<>(searchIdResponse, HttpStatus.OK);
    }

    // 비밀번호 찾기
    // 이메일 임시 비밀번호 전송
    @ApiOperation(value = "유저 비밀번호 찾기", notes = "Id, Email을 통한 임시 비밀번호 발송")
    @PostMapping("/user/searchPwd")
    public ResponseEntity<?> userSearchPwd(@Valid @RequestBody SearchPwdDto searchPwdDto){
        String msg = accountService.searchPwd(searchPwdDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // 비밀번호 변경
    @ApiOperation(value = "마이페이지 비밀번호 변경", notes = "기존 비밀번호 변경")
    @PostMapping("/login/changePwd")
    public ResponseEntity<?> changePwd(@Valid @RequestBody UserAccountPwdDto userAccountPwdDto,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails){
        UserAccount user = principalDetails.getUser();
        String msg = accountService.chagePwd(userAccountPwdDto, user);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // 개인정보 변경
    @ApiOperation(value = "마이페이지 개인정보 변경", notes = "Email, phoneNumber 변경 가능")
    @PostMapping("/login/changeInfo")
    public ResponseEntity<?> changeInfo(@Valid @RequestBody UserAccountInfoDto userAccountInfoDto,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails){
        UserAccount user = principalDetails.getUser();
        String msg = accountService.chageInformaition(userAccountInfoDto, user);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @ApiOperation(value = "유저 개인정보 조회", notes = "유저 개인 정보 조회")
    @GetMapping("/login/info")
    public ResponseEntity<?> findInfo(@AuthenticationPrincipal PrincipalDetails principalDetails){
        UserAccount user = principalDetails.getUser();
        UserAccountDto user1 = accountService.findUser(user);
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }

    @ApiOperation(value = "RefreshToken 재발급", notes = "AccessToken 만료 시 RefreshToken 재발급")
    @GetMapping("/user/reissue/token")
    public ResponseEntity<?> reissueToken(@CookieValue(required = false) String refreshToken){
        String reissueToken = tokenService.validateRefreshToken(refreshToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + reissueToken);
        return new ResponseEntity<>("토큰 발급 성공" ,headers ,HttpStatus.OK);
    }
}
