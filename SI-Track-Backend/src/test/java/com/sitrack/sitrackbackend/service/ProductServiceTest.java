package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import com.sitrack.sitrackbackend.repository.ProductRepository;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService sut;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserAccountRepository userAccountRepository;

    @DisplayName("[ProductS] 상품 등록 성공")
    @Test
    public void register_product(){
        // Given
        UserAccount userAccount = createUserAccount();
        ProductDto productdto = createProductDto();
        String userId = productdto.userAccountdto().userId();
        given(userAccountRepository.findByUserId(userId)).willReturn(userAccount);

        // When
        String result = sut.register_product(productdto);

        // Then
        then(productRepository).should().save(any());
        assertThat(result).isEqualTo("상품 등록 완료");

    }

    @DisplayName("[ProductS] 업데이트 성공")
    @Test
    public void update_product_success(){
        // Given
        Long productId = 1L;
        Product product = createProduct();
        ProductDto productdto = createProductDto();

        given(productRepository.getReferenceById(productId)).willReturn(product);

        // When
        String result = sut.update_product(productId, productdto);

        // Then
        then(productRepository).should().save(any());
        assertThat(result).isEqualTo("상품 업데이트 완료");

    }

    @DisplayName("[ProductS] 기존 상품 담당자와 아이디가 달라 업데이트 실패")
    @Test
    public void update_product_fail(){
        // Given
        Long productId = 1L;
        Product diffproduct = createProduct();
        diffproduct.setUserAccount(createUserAccount("user2"));
        ProductDto productdto = createProductDto();

        given(productRepository.getReferenceById(productId)).willReturn(diffproduct);

        // When
        String result = sut.update_product(productId, productdto);

        // Then
        assertThat(result).isEqualTo("권한이 없습니다.");
    }

    @DisplayName("[ProductS] 상품 등록자와 삭제자와 같아 삭제 성공")
    @Test
    public void delete_product(){
        // Given
        Long productId = 1L;
        String userId = "user1";
        Product product = createProduct();
        given(productRepository.getReferenceById(productId)).willReturn(product);

        // When
        String result = sut.delete_product(productId,userId);

        // Then
        then(productRepository).should().deleteById(any());
        assertThat(result).isEqualTo("상품 삭제 완료");
    }

    @DisplayName("[ProductS] Find_One Test")
    @Test
    public void find_one_product(){
        // Given
        Long productId = 1L;
        Product product = createProduct();
        given(productRepository.findById(productId)).willReturn(Optional.of(product));

        // When
        sut.findbyId_product_one(productId);

        // Then
        then(productRepository).should().findById(any());
    }

    @DisplayName("[ProductS] Find_All Test")
    @Test
    public void find_all_product(){
        // Given
        Long productId = 1L;
        List<Product> products = new ArrayList<>();
        for(int i=0; i<2; i++)
            products.add(createProduct());

        Product product = createProduct();
        given(productRepository.findAll()).willReturn(products);

        // When
        List<Product> products1 = sut.findbyId_product_all();

        // Then
        assertThat(products).isEqualTo(products1);
        then(productRepository).should().findAll();
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

    private UserAccount createUserAccount(String userId){
        return UserAccount.of(
                userId,
                "1234",
                "권용호",
                "b2b@naver.com",
                "010-1111-2222"
        );
    }
    private UserAccount createUserAccount() {
        return createUserAccount("user1");
    }

    private ProductDto createProductDto() {
        return ProductDto.of(
                createUserAccountDto(),
                1L,
                "A12",
                "볼펜",
                100L,
                1000L,
                "볼펜이다",
                1000L,
                100L
        );
    }

    private Product createProduct(){
        return Product.of(
                createUserAccount(),
                1L,
                "A12",
                "볼펜",
                100L,
                1000L,
                "볼펜이다",
                1000L,
                100L
        );
    }
}
