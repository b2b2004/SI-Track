package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.ProductImage;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.domain.constant.ProductImageType;
import com.sitrack.sitrackbackend.domain.constant.RoleType;
import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.ProductImageDto;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import com.sitrack.sitrackbackend.dto.response.ProductResponse;
import com.sitrack.sitrackbackend.repository.ProductRepository;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sitrack.sitrackbackend.domain.constant.ProductImageType.Subnail;
import static com.sitrack.sitrackbackend.domain.constant.ProductImageType.Thumbnail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService sut;

    @Mock
    private ImageService imageService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserAccountRepository userAccountRepository;

    @DisplayName("[ProductS] 상품 등록 성공")
    @Test
    public void register_product_has_role() throws IOException {
        // Given
        UserAccount userAccount = createUserAccount();
        userAccount.setRoleType(RoleType.ADMIN);
        ProductDto productdto = createProductDto();
        String userId = productdto.userAccountdto().userId();
        List<MultipartFile> multipartFiles = createMultipartImage();
        List<ProductImageDto> productImageDtos = createProductImageDto();

        given(userAccountRepository.findByUserId(userId)).willReturn(Optional.of(userAccount));
        given(imageService.parseImageFile(multipartFiles)).willReturn(productImageDtos);

        // When
        String result = sut.register_product(productdto, multipartFiles);

        // Then
        then(productRepository).should().save(any());
        then(imageService).should().parseImageFile(multipartFiles);
        assertThat(result).isEqualTo("상품 등록 완료");
    }

    @DisplayName("[ProductS] 권한이 없어 상품 등록 실패")
    @Test
    public void register_product_has_noRole() throws IOException {
        // Given
        UserAccount userAccount = createUserAccount();
        ProductDto productdto = createProductDto();
        String userId = productdto.userAccountdto().userId();
        List<MultipartFile> multipartFiles = createMultipartImage();

        given(userAccountRepository.findByUserId(userId)).willReturn(Optional.of(userAccount));

        // When
        String result = sut.register_product(productdto, multipartFiles);

        // Then
        assertThat(result).isEqualTo("권한이 없습니다.");
    }

    @DisplayName("[ProductS] 업데이트 성공")
    @Test
    public void update_product_success() throws IOException {
        // Given
        Product product = createProduct();
        ProductDto productdto = createProductDto(10L,"지우개");
        UserAccount user = createUserAccount();
        user.setRoleType(RoleType.ADMIN);
        List<MultipartFile> multipartFiles = createMultipartImage();
        List<ProductImageDto> productImageDtos = createProductImageDto();


        given(productRepository.getReferenceById(productdto.productId())).willReturn(product);
        willDoNothing().given(imageService).delete_By_product_id(any());
        given(imageService.parseImageFile(multipartFiles)).willReturn(productImageDtos);
        given(userAccountRepository.findByUserId(any())).willReturn(Optional.of(user));

        // When
        String result = sut.update_product(productdto.productId(), productdto, multipartFiles);

        // Then
        assertThat(result).isEqualTo("상품 업데이트 완료");
        assertThat(product)
                .hasFieldOrPropertyWithValue("categoryId", productdto.categoryId())
                .hasFieldOrPropertyWithValue("productDetail", productdto.productDetail());
        then(productRepository).should().getReferenceById(productdto.productId());
        then(imageService).should().delete_By_product_id(any());
        then(productRepository).should().save(any());
    }

    @DisplayName("[ProductS] 권한이 없어 업데이트 실패")
    @Test
    public void update_product_fail(){
        // Given
        Long productId = 1L;
        Product product = createProduct();
        UserAccount user = createUserAccount();
        List<MultipartFile> multipartFiles = createMultipartImage();
        ProductDto productdto = createProductDto();

        given(productRepository.getReferenceById(productId)).willReturn(product);
        given(userAccountRepository.findByUserId(any())).willReturn(Optional.of(user));
        // When
        String result = sut.update_product(productId, productdto, multipartFiles);

        // Then
        assertThat(result).isEqualTo("권한이 없습니다.");
    }

    @DisplayName("[ProductS] 상품 삭제 성공")
    @Test
    public void delete_product(){
        // Given
        Long productId = 1L;
        UserAccount user = createUserAccount();
        user.setRoleType(RoleType.ADMIN);
        Product product = createProduct();

        given(userAccountRepository.findByUserId(any())).willReturn(Optional.of(user));
        // When
        String result = sut.delete_product(productId, user);

        // Then
        then(productRepository).should().deleteById(any());
        assertThat(result).isEqualTo("상품 삭제 완료");
    }

    @DisplayName("[ProductS] 권한이 없어 상품 삭제 실패")
    @Test
    public void delete_product_has_noRole(){
        // Given
        Long productId = 1L;
        UserAccount user = createUserAccount();
        Product product = createProduct();

        given(userAccountRepository.findByUserId(any())).willReturn(Optional.of(user));
        // When
        String result = sut.delete_product(productId, user);

        // Then
        assertThat(result).isEqualTo("권한이 없습니다.");
    }

    @DisplayName("[ProductS] 상품 조회 시 상품 반환")
    @Test
    public void find_one_product(){
        // Given
        Long productId = 1L;
        Product product = createProduct();
        given(productRepository.findById(productId)).willReturn(Optional.of(product));

        // When
        ProductResponse productResponse = sut.findbyId_product_one(productId);

        // Then
        assertThat(productResponse)
                .hasFieldOrPropertyWithValue("supplierCode", product.getSupplierCode())
                .hasFieldOrPropertyWithValue("productName", product.getProductName())
                .hasFieldOrPropertyWithValue("productDetail", product.getProductDetail());
        then(productRepository).should().findById(productId);

    }

    @DisplayName("[ProductS] 없는 상품 조회 시 예외를 반환")
    @Test
    void find_one_nonproduct() {
        // Given
        Long productId = 1L;
        given(productRepository.findById(productId)).willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(() -> sut.findbyId_product_one(productId));

        // Then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("상품이 없습니다 - productId: " + productId);
        then(productRepository).should().findById(productId);
    }

    @DisplayName("[ProductS] 검색어 없이 상품을 검색하면, 게시글 페이지를 반환")
    @Test
    public void find_all_product_no_searchValue() {
        // Given
        Pageable pageable = Pageable.ofSize(20);
        given(productRepository.findAll(pageable)).willReturn(Page.empty());

        // When
        Page<ProductDto> productDtos = sut.findbyId_product_all_and_search(pageable, null);

        // Then
        assertThat(productDtos).isEmpty();
        then(productRepository).should().findAll(pageable);
    }

    @DisplayName("[ProductS] 검색어와 함께 상품을 검색하면, 상품 페이지를 반환")
    @Test
    public void find_all_product_with_searchValue() {
        // Given
        Pageable pageable = Pageable.ofSize(20);
        String searchValue = "종이";
        given(productRepository.findByproductNameContaining(searchValue, pageable)).willReturn(Page.empty());

        // When
        Page<ProductDto> productDtos = sut.findbyId_product_all_and_search(pageable, searchValue);

        // Then
        assertThat(productDtos).isEmpty();
        then(productRepository).should().findByproductNameContaining(searchValue, pageable);
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

    private ProductDto createProductDto(Long categoryId, String productDetail) {
        return ProductDto.of(
                createUserAccountDto(),
                categoryId,
                "A12",
                "볼펜",
                100L,
                1000L,
                productDetail,
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

    private ProductResponse createProductResponse(){
        return ProductResponse.of(
                "A12",
                "볼펜",
                100L,
                "볼펜이다",
                createProductImageDto()
        );
    }

    private List<ProductImageDto> createProductImageDto(){
        return List.of(
                new ProductImageDto("testImage1", "testImage1", "src/test/resources/testImage/", Thumbnail),
                new ProductImageDto("testImage2", "testImage2", "src/test/resources/testImage/", Subnail),
                new ProductImageDto("testImage3", "testImage3", "src/test/resources/testImage/", Subnail)
        );
    }

    private List<MultipartFile> createMultipartImage(){
        return List.of(
                new MockMultipartFile("testImage1", "testImage3.PNG", MediaType.IMAGE_PNG_VALUE, "test1".getBytes()),
                new MockMultipartFile("testImage2", "testImage3.PNG", MediaType.IMAGE_PNG_VALUE, "test2".getBytes()),
                new MockMultipartFile("testImage3", "testImage3.PNG", MediaType.IMAGE_PNG_VALUE, "test2".getBytes())
        );
    }
}
