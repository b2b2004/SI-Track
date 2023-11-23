package com.sitrack.sitrackbackend.repository;


import com.sitrack.sitrackbackend.config.JpaConfig;
import com.sitrack.sitrackbackend.domain.Category;
import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.Supplier;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Import(JpaConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    private final UserAccountRepository userAccountRepository;
    private final ProductRepository productRepository;

    public ProductRepositoryTest(@Autowired UserAccountRepository userAccountRepository,
                                 @Autowired ProductRepository productRepository) {
        this.userAccountRepository = userAccountRepository;
        this.productRepository = productRepository;
    }

    @DisplayName("[PrdouctR] Select Test")
    @Test
    void product_select_test() {
        // Given
        // When
        List<Product> products = productRepository.findAll();
        // Then
        assertThat(products)
                .isNotNull()
                .hasSize(3);
    }

    @DisplayName("[PrdouctR] Insert Test")
    @Test
    void product_insert_test() {
        // Given
        long previousCount = productRepository.count();
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("user4", "1234", "권용호", "b@naver.com", "010-1111-2222"));
        Product product = Product.of(userAccount, createCategory(), createSupplier(), "종이", 1000L, 2000L, "종이다", 100L, 5L);

        // When
        productRepository.save(product);

        // Then
        assertThat(productRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("[PrdouctR] Update Test")
    @Test
    void product_update_test(){
        // Given
        Product product = productRepository.findById(1L).orElseThrow();
        product.setProductName("수정");

        // When
        Product savedProduct = productRepository.saveAndFlush(product);

        // Then
        assertThat(savedProduct.getProductName()).isEqualTo("수정");
    }

    @DisplayName("[PrdouctR] Delete Test")
    @Test
    void product_delete_test(){
        // Given
        Product product = productRepository.findById(1L).orElseThrow();
        long previousArticleCount = productRepository.count(); // 게시글 수

        // When
        productRepository.delete(product);

        // Then
        assertThat(productRepository.count()).isEqualTo( previousArticleCount - 1);
    }

    private Category createCategory(){
        return Category.of(
                1L,
                "물류"
        );
    }

    private Supplier createSupplier(){
        return Supplier.of(
                1L,
                "공급업체1",
                "A12"
        );
    }
}