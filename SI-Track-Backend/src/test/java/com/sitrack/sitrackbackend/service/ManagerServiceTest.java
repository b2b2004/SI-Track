package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.domain.constant.RoleType;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {

    @InjectMocks
    private ManagerService sut;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Test
    @DisplayName("[ManagerS] ADMIN 권한 설정 성공")
    public void update_userRole_success(){
        // Given
        UserAccount admin = createUserAccount("admin");
        admin.setRoleType(RoleType.MANAGER);
        UserAccount user = createUserAccount("user1");
        UserAccountDto userAccountDto = createUserAccountDto(RoleType.ADMIN);
        given(userAccountRepository.findByUserId(userAccountDto.userId())).willReturn(Optional.of(user));

        // When
        String result = sut.setRole(userAccountDto, admin);

        // Then
        assertThat(result).isEqualTo("권한 설정 완료");
        then(userAccountRepository).should().findByUserId(userAccountDto.userId());
    }

    @Test
    @DisplayName("[ManagerS] MANAGER가 아니여서 권한 설정 실패")
    public void update_userRole_hasNoRole_fail(){
        // Given
        UserAccount admin = createUserAccount("admin");
        admin.setRoleType(RoleType.ADMIN);
        UserAccountDto userAccountDto = createUserAccountDto(RoleType.ADMIN);

        // When
        String result = sut.setRole(userAccountDto, admin);

        // Then
        assertThat(result).isEqualTo("권한이 없습니다. 관리자에게 문의하세요.");
    }

    @Test
    @DisplayName("[ManagerS] 권한 설정 값이 Null일 경우 권한 업데이트 실패")
    public void update_userRole_nullRole_fail(){
        // Given
        UserAccount admin = createUserAccount("admin");
        admin.setRoleType(RoleType.MANAGER);
        UserAccount user = createUserAccount("user1");
        UserAccountDto userAccountDto = createUserAccountDto(null);
        given(userAccountRepository.findByUserId(userAccountDto.userId())).willReturn(Optional.of(user));

        // When
        String result = sut.setRole(userAccountDto, admin);

        // Then
        assertThat(result).isEqualTo("권한 설정 실패");
        then(userAccountRepository).should().findByUserId(userAccountDto.userId());
    }

    private UserAccount createUserAccount(String userId) {
        return UserAccount.of(
                userId,
                "1234",
                "권용호",
                "test@naver.com",
                "010-1111-2222"
        );
    }

    private UserAccountDto createUserAccountDto(RoleType roleType) {
        return UserAccountDto.of(
                "user1",
                "1234",
                "권용호",
                "b2b@naver.com",
                "010-1111-2222",
                roleType,
                LocalDateTime.now(),
                "kwon",
                LocalDateTime.now(),
                "kwon"
        );
    }
}
