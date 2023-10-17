package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.LoginDto;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService sut;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;


    @Test
    public void signup_success(){
        // Given
        UserAccountDto userdto = createUserAccountDto();

        // When
        String result = sut.signup(userdto);

        // When
        assertThat(sut.signup(userdto)).isEqualTo("회원 가입 완료");
    }

    @Test
    public void login_success(){

    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "user1",
                "1234",
                "권용호",
                "b2b@naver.com",
                "010-1111-2222",
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
                "user1",
                "1234",
                "권용호",
                "b2b@naver.com",
                "010-1111-2222"
        );
    }
}