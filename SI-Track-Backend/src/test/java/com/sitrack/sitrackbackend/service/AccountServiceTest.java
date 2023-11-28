package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.Exception.CustomException;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.domain.constant.RoleType;
import com.sitrack.sitrackbackend.dto.*;
import com.sitrack.sitrackbackend.dto.response.SearchIdResponse;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService sut;

    @Mock
    private EmailService emailService;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;


    @Test
    @DisplayName("[AccountS] 회원 가입 성공")
    public void signup_success(){
        // Given
        UserAccountDto userdto = createUserAccountDto();

        // When
        String result = sut.signup(userdto);

        // When
        assertThat(sut.signup(userdto)).isEqualTo("회원 가입 완료");
    }

    @Test
    @DisplayName("[AccountS] 아이디 찾기 성공")
    public void searchId_success(){
        // Given
        SearchIdDto searchIdDto = SearchIdDto.of("test1", "test@naver.com");
        UserAccount user = createUserAccount();
        given(userAccountRepository.findByUserNameAndUserEmail(any(), any())).willReturn(Optional.of(user));
        SearchIdResponse searchIdResponse = SearchIdResponse.of(user.getUserId(), user.getCreatedAt());

        // When
        sut.searchId(searchIdDto);

        // Then
        assertThat(searchIdResponse.userId()).isEqualTo(user.getUserId());
    }


    @Test
    @DisplayName("[AccountS] 아이디가 존재하지 않아 아이디 찾기 실패")
    public void searchId_has_NoId(){
        // Given
        SearchIdDto searchIdDto = SearchIdDto.of("test2", "test@naver.com");
        given(userAccountRepository.findByUserNameAndUserEmail(searchIdDto.userName(), searchIdDto.userEmail())).willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(() -> sut.searchId(searchIdDto));

        // Then
        assertThat(t)
                .isInstanceOf(CustomException.class);
        then(userAccountRepository).should().findByUserNameAndUserEmail(searchIdDto.userName(), searchIdDto.userEmail());
    }


    @Test
    @DisplayName("[AccountS] 임시 비밀번호 발송 성공")
    public void searchPwd_success(){
        // Given
        SearchPwdDto searchPwdDto = SearchPwdDto.of("test1", "test@naver.com");
        UserAccount user = createUserAccount();
        given(userAccountRepository.findByUserIdAndUserEmail(any(), any())).willReturn(Optional.of(user));
        given(emailService.createMailAndcreatePWD(user.getUserName(), user.getUserEmail())).willReturn("password");

        // When
        String result = sut.searchPwd(searchPwdDto);

        // Then
        assertThat(result).isEqualTo("이메일로 임시 비밀 번호가 발송 되었습니다.");
    }

    @Test
    @DisplayName("[AccountS] 아이디가 존재하지 않아 임시 비밀번호 발송 실패")
    public void searchPwd_has_NoId(){
        // Given
        SearchPwdDto searchPwdDto = SearchPwdDto.of("test1", "test@naver.com");
        given(userAccountRepository.findByUserIdAndUserEmail(any(), any())).willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(() -> sut.searchPwd(searchPwdDto));

        // Then
        assertThat(t)
                .isInstanceOf(CustomException.class);
        then(userAccountRepository).should().findByUserIdAndUserEmail(any(), any());
    }

    @Test
    @DisplayName("[AccountS] 비밀번호 일치 비밀번호 변경")
    public void chagePwd_success(){
        // Given
        UserAccount user = createUserAccount();
        UserAccountPwdDto userAccountPwdDto = UserAccountPwdDto.of("1234", "2345");
        given(userAccountRepository.findByUserId(user.getUserId())).willReturn(Optional.of(user));

        // When
        String result = sut.chagePwd(userAccountPwdDto, user);

        // Then
        assertThat(result).isEqualTo("비밀번호가 변경 되었습니다.");
        assertThat(user.getUserPassword()).isNotEqualTo(userAccountPwdDto.newPwd());
    }

    @Test
    @DisplayName("[AccountS] 비밀번호가 일치하지 않아 비밀번호 변경 실패")
    public void chagePwd_NotSamePwd(){
        // Given
        UserAccount user = createUserAccount();
        UserAccountPwdDto userAccountPwdDto = UserAccountPwdDto.of("1111", "2345");
        given(userAccountRepository.findByUserId(user.getUserId())).willReturn(Optional.of(user));

        // When
        String result = sut.chagePwd(userAccountPwdDto, user);

        // Then
        assertThat(result).isEqualTo("현재 비밀번호와 일치하지 않습니다.");
    }

    @Test
    @DisplayName("[AccountS] 개인 정보 변경 성공")
    public void chageInformaition_success(){
        // Given
        UserAccount user = createUserAccount();
        UserAccountInfoDto userAccountInfoDto = UserAccountInfoDto.of("chage@naver.com", "010-9999-9999");
        given(userAccountRepository.findByUserId(user.getUserId())).willReturn(Optional.of(user));

        // When
        String result = sut.chageInformaition(userAccountInfoDto, user);

        // Then
        assertThat(result).isEqualTo("정보가 변경 되었습니다.");
        assertThat(user.getUserEmail()).isEqualTo(userAccountInfoDto.userEmail());
        assertThat(user.getUserPhoneNumber()).isEqualTo(userAccountInfoDto.userPhoneNumber());
    }



    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "user1",
                "1234",
                "권용호",
                "b2b@naver.com",
                "010-1111-2222",
                RoleType.USER,
                LocalDateTime.now(),
                "kwon",
                LocalDateTime.now(),
                "kwon"
        );
    }

    private LoginDto createLoginDto(){
        return LoginDto.of(
                "user1",
                "1234"
        );
    }

    private UserAccount createUserAccount(){
        return UserAccount.of(
                "test1",
                "1234",
                "권용호",
                "test@naver.com",
                "010-1111-2222"
        );
    }
}