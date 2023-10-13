package com.sitrack.sitrackbackend.controller;

import com.sitrack.sitrackbackend.dto.LoginDto;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import com.sitrack.sitrackbackend.service.AccountService;
import lombok.RequiredArgsConstructor;
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
    public String userAccountLogin(@RequestBody LoginDto loginDto){
        return accountService.login(loginDto);
    }

    @PostMapping("/user/signup")
    public String userAccountSignup(@RequestBody UserAccountDto userAccountDto){
        return accountService.signup(userAccountDto);
    }
}
