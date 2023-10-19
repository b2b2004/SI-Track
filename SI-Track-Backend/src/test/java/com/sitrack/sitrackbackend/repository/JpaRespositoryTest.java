package com.sitrack.sitrackbackend.repository;

import com.sitrack.sitrackbackend.config.JpaConfig;
import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpaRespositoryTest {

    private final UserAccountRepository userAccountRepository;

    public JpaRespositoryTest(@Autowired UserAccountRepository userAccountRepository,
                              @Autowired ProductRepository productRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("[user] Create Test")
    @Test
    public void userAccount_signup(){
        // Given
        long previousCount = userAccountRepository.count();

        // When
        UserAccount userAccount = UserAccount.of("kwon","123123","권용호","b2b2004@nate.com","010-1111-2222");
        userAccountRepository.save(userAccount);

        // Then
        assertThat(userAccountRepository.count()).isEqualTo(previousCount + 1);
    }

}