package com.sitrack.sitrackbackend.controller;

import com.sitrack.sitrackbackend.config.security.auth.PrincipalDetails;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import com.sitrack.sitrackbackend.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/manager")
@Controller
public class ManagerController {

    private final ManagerService managerService;

    // MANAGER은 ADMIN 설정 가능
    @PostMapping("/update/role")
    public ResponseEntity<?> updateRole(@RequestBody UserAccountDto userAccountDto,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails){
        UserAccount user = principalDetails.getUser();
        String msg = managerService.setRole(userAccountDto, user);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
