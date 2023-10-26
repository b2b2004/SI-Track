package com.sitrack.sitrackbackend.controller;

import com.sitrack.sitrackbackend.dto.LoginDto;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import com.sitrack.sitrackbackend.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/account")
@Controller
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<?> userAccountLogin(@RequestBody LoginDto loginDto){
        return new ResponseEntity<>(accountService.login(loginDto), HttpStatus.OK);
    }

    @PostMapping("/user/signup")
    public ResponseEntity<?> userAccountSignup(@RequestBody UserAccountDto userAccountDto){
        String msg = accountService.signup(userAccountDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
