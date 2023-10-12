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
    private final ProductRepository productRepository;

    public JpaRespositoryTest(@Autowired UserAccountRepository userAccountRepository,
                              @Autowired ProductRepository productRepository) {
        this.userAccountRepository = userAccountRepository;
        this.productRepository = productRepository;
    }

    @Test
    public void 회원가입하기(){
        // Given
        long previousCount = userAccountRepository.count();

        // When
        UserAccount userAccount = UserAccount.of("kwon","123123","권용호","b2b2004@nate.com","010-1111-2222");
        userAccountRepository.save(userAccount);

        // Then
        assertThat(userAccountRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("insert test")
    @Test
    void product_insert_tset(){
        // Given
        long previousCount = productRepository.count();

        // When
        Product product = Product.of("종이",1000L, 900L, "종이다", 10L, 9L);
        productRepository.save(product);

        // Then
        assertThat(productRepository.count()).isEqualTo(previousCount + 1);
    }

}