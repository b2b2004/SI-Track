package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.domain.constant.RoleType;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ManagerService {

    private final UserAccountRepository userAccountRepository;

    public String setRole(UserAccountDto userAccountDto, UserAccount user) {
        if(user.getRoleType() != RoleType.MANAGER){
            return "권한이 없습니다. 관리자에게 문의하세요.";
        }
        UserAccount user1 = userAccountRepository.findByUserId(userAccountDto.userId()).orElseThrow();
        if(userAccountDto.roleType() != null){
            user1.setRoleType(userAccountDto.roleType());
        }else{
            return "권한 설정 실패";
        }
        return "권한 설정 완료";
    }
}
