package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.Exception.CustomException;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.domain.constant.RoleType;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import com.sitrack.sitrackbackend.repository.UserAccountRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sitrack.sitrackbackend.Exception.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Transactional
@Service
public class ManagerService {

    private final UserAccountRepositoryCustom userAccountRepositoryCustom;

    public String setRole(UserAccountDto userAccountDto, UserAccount user) {
        if(user.getRoleType() != RoleType.MANAGER){
            return "권한이 없습니다. 관리자에게 문의하세요.";
        }
        UserAccount user1 = userAccountRepositoryCustom.findByUserId(userAccountDto.userId())
                .orElseThrow(()-> new CustomException(USER_NOT_FOUND));
        if(userAccountDto.roleType() != null){
            user1.setRoleType(userAccountDto.roleType());
        }else{
            return "권한 설정 실패";
        }
        return "권한 설정 완료";
    }
}
