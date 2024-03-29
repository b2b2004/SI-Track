package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.Exception.CustomException;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.domain.constant.RoleType;
import com.sitrack.sitrackbackend.dto.*;
import com.sitrack.sitrackbackend.dto.response.SearchIdResponse;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import com.sitrack.sitrackbackend.repository.UserAccountRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sitrack.sitrackbackend.Exception.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountService {

    private final UserAccountRepositoryCustom userAccountRepositoryCustom;
    private final UserAccountRepository userAccountRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String signup(UserAccountDto userAccountDto) {
        String rawPassword = userAccountDto.userPassword();
        UserAccount userAccount = userAccountDto.toEntity();
        userAccount.setUserPassword(bCryptPasswordEncoder.encode(rawPassword));
        
        // 테스트 편의를 위해 변경
        userAccount.setRoleType(RoleType.ADMIN);
        userAccountRepository.save(userAccount);
        return "회원 가입 완료";
    }

    public SearchIdResponse searchId(SearchIdDto searchIdDto) {
        // 0.420 2000번 / 500개의 데이터
        // 0.163 개선
        SearchIdResponse searchIdResponse = userAccountRepositoryCustom.findByUserNameAndUserEmail(searchIdDto.userName(), searchIdDto.userEmail())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        return searchIdResponse;
    }

    public String searchPwd(SearchPwdDto searchPwdDto) {
        UserAccount user = userAccountRepositoryCustom.findByUserIdAndUserEmail(searchPwdDto.userId(), searchPwdDto.userEmail())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        String newPWD = emailService.createMailAndcreatePWD(user.getUserName(), user.getUserEmail());
        user.setUserPassword(bCryptPasswordEncoder.encode(newPWD));
        return "이메일로 임시 비밀 번호가 발송 되었습니다.";
    }

    public String chagePwd(UserAccountPwdDto userAccountPwdDto, UserAccount user) {
        if(!userAccountPwdDto.nowPwd().equals(user.getUserPassword())){
            return "현재 비밀번호와 일치하지 않습니다.";
        }
        user.setUserPassword(bCryptPasswordEncoder.encode(userAccountPwdDto.newPwd()));
        return "비밀번호가 변경 되었습니다.";
    }

    public String chageInformaition(UserAccountInfoDto userAccountInfoDto, UserAccount user1) {
        UserAccount user = userAccountRepositoryCustom.findByUserId(user1.getUserId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        user.setUserEmail(userAccountInfoDto.userEmail());
        user.setUserPhoneNumber(userAccountInfoDto.userPhoneNumber());

        return "정보가 변경 되었습니다.";
    }

    @Transactional(readOnly = true)
    public UserAccountDto findUser(UserAccount user) {
//        UserAccountDto user1 = userAccountRepositoryCustom.findByUserId(user.getUserId())
//                .map(UserAccountDto::from)
//                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        return UserAccountDto.from(user);
    }
}
