package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.domain.*;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.domain.constant.RoleType;
import com.sitrack.sitrackbackend.dto.*;
import com.sitrack.sitrackbackend.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @InjectMocks
    private AdminService sut;

    @Mock
    private UserAccountRepositoryCustom userAccountRepositoryCustom;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductRepositoryCustom productRepositoryCustom;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderRepositoryCustom orderRepositoryCustom;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private SupplierRepositoryCustom supplierRepositoryCustom;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("[AdminS] 검색어 없이 모든 유저 출력")
    public void findAll_user_success() {

        // Given
        Pageable pageable = Pageable.ofSize(20);
        given(userAccountRepositoryCustom.searchPageComplex(null, null, pageable)).willReturn(Page.empty());
        // When
        Page<UserAccountDto> users = sut.findUsersWithSearchType(null, null, pageable);

        // Then
        assertThat(users).isEmpty();
        then(userAccountRepositoryCustom).should().searchPageComplex(null, null, pageable);
    }

    @Test
    @DisplayName("[AdminS] 검색어 없이 모든 상품 출력")
    public void findAll_product_success() {
        // Given
        Pageable pageable = Pageable.ofSize(20);
        given(productRepositoryCustom.searchPageComplex(null, null, pageable)).willReturn(Page.empty());
        // When
        Page<AdminProductDto> products = sut.findProductsWithSearchType(null, null, pageable);

        // Then
        assertThat(products).isEmpty();
        then(productRepositoryCustom).should().searchPageComplex(null, null, pageable);
    }

    @Test
    @DisplayName("[AdminS] 검색어 없이 모든 주문 출력")
    public void findAll_order_success() {
        // Given
        Pageable pageable = Pageable.ofSize(20);
        given(orderRepositoryCustom.searchPageComplex(null, null, pageable)).willReturn(Page.empty());
        // When
        Page<OrderDto> orders = sut.findOrdersWithSearchType(null, null, pageable);

        // Then
        assertThat(orders).isEmpty();
        then(orderRepositoryCustom).should().searchPageComplex(null, null, pageable);
    }

    @Test
    @DisplayName("[AdminS] 검색어 없이 모든 공급업체 출력")
    public void findAll_supplier_success() {
        // Given
        Pageable pageable = Pageable.ofSize(20);
        given(supplierRepositoryCustom.searchPageComplex(null, null, pageable)).willReturn(Page.empty());
        // When
        Page<SupplierDto> suppliers = sut.findSuppliersWithSearchType(null, null, pageable);

        // Then
        assertThat(suppliers).isEmpty();
        then(supplierRepositoryCustom).should().searchPageComplex(null, null, pageable);
    }

    @Test
    @DisplayName("[AdminS] 모든 카테고리 확인")
    public void findAll_category_success() {
        List<Category> categoryList = List.of(createCategory());
        given(categoryRepository.findAll()).willReturn(categoryList);

        List<CategoryDto> categoryDtoList = sut.findAllCategory();

        assertThat(categoryDtoList)
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("categoryName", categoryList.get(0).getCategoryName());
        then(categoryRepository).should().findAll();
    }

    @Test
    @DisplayName("[AdminS] 단일 상품 업데이트 성공")
    public void update_product_success(){
        // Given
        Product product = createProduct("볼펜");
        AdminProductDto adminProductDto = createAdminProductDto("양말");
        given(productRepository.findById(adminProductDto.productId())).willReturn(Optional.of(product));

        // When
        String result = sut.updateProduct(adminProductDto);

        // Then
        assertThat(result).isEqualTo("상품 업데이트 완료");
    }

    @Test
    @DisplayName("[AdminS] 주문 상태 업데이트 성공")
    public void update_orderStatus_success(){
        // Given
        Order order = createOrder("test1");
        OrderDto orderDto = createOrderDto("test1","주문 시작");
        given(orderRepository.findById(orderDto.orderId())).willReturn(Optional.of(order));

        // When
        String result = sut.updateOrder(orderDto);

        // Then
        assertThat(result).isEqualTo("주문 현황 업데이트 완료");
    }

    @Test
    @DisplayName("[AdminS] 주문 상태 업데이트 실패")
    public void update_orderStatus_hasNull_fail(){
        // Given
        Order order = createOrder("test1");
        OrderDto orderDto = createOrderDto("test1", null);

        given(orderRepository.findById(orderDto.orderId())).willReturn(Optional.of(order));

        // When
        String result = sut.updateOrder(orderDto);

        // Then
        assertThat(result).isEqualTo("업데이트 실패");
    }

    @Test
    @DisplayName("[AdminS] 공급업체 업데이트 성공")
    public void update_Supplier_success(){
        // Given
        SupplierDto supplierDto = createSupplierDto("공급업체2");
        Supplier supplier = createSupplier();
        given(supplierRepository.findById(supplierDto.supplierId())).willReturn(Optional.of(supplier));

        // When
        String result = sut.updateSupplier(supplierDto);

        // Then
        assertThat(result).isEqualTo("공급업체 정보 수정 완료");
    }

    @Test
    @DisplayName("[AdminS] 카테고리 업데이트 성공")
    public void update_Category_success(){
        // Given
        CategoryDto categoryDto = createCategoryDto("사무용품");
        Category category = createCategory();
        given(categoryRepository.findById(categoryDto.categoryId())).willReturn(Optional.of(category));

        // When
        String result = sut.updateCategory(categoryDto);

        // Then
        assertThat(result).isEqualTo("카테고리 정보 수정 완료");
    }

    @Test
    @DisplayName("[AdminS] 카테고리 업데이트 실패")
    public void update_Category_hasNull_fail(){
        // Given
        CategoryDto categoryDto = createCategoryDto("");
        Category category = createCategory();
        given(categoryRepository.findById(categoryDto.categoryId())).willReturn(Optional.of(category));

        // When
        String result = sut.updateCategory(categoryDto);

        // Then
        assertThat(result).isEqualTo("카테고리 이름을 확인해주세요.");
    }

    @Test
    @DisplayName("[AdminS] 공급업체 생성 성공")
    public void register_Supplier_success(){
        // Given
        SupplierDto supplierDto = createSupplierDto("공급업체");

        // When
        String result = sut.registerSupplier(supplierDto);

        // Then
        assertThat(result).isEqualTo("등록 성공");
        then(supplierRepository).should().save(any());
    }

    @Test
    @DisplayName("[AdminS] 카테고리 생성 성공")
    public void register_Category_success(){
        // Given
        CategoryDto categoryDto = createCategoryDto("사무용품");

        // When
        String result = sut.registerCategory(categoryDto);

        // Then
        assertThat(result).isEqualTo("등록 성공");
        then(categoryRepository).should().save(any());
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

    private Product createProduct(String productName) {
        return Product.of(
                createUserAccount("test1"),
                createCategory(),
                createSupplier(),
                productName,
                100L,
                1000L,
                "볼펜이다",
                1000L,
                100L
        );
    }

    private Order createOrder(String recipient) {
        return Order.of(
                createUserAccount("test1"),
                1000L,
                "우리집",
                "배송 대기",
                "조심히 배송",
                recipient,
                "010-1111-2222",
                List.of(OrderItem.of(createProduct("볼펜"), 2L, 1000L))
        );
    }

    private OrderDto createOrderDto(String recipient, String orderStatus){
        return OrderDto.of(
                1L,
                "test1",
                1000L,
                "우리집",
                orderStatus,
                "조심히 배송",
                recipient,
                "010-1111-2222",
                LocalDateTime.now(),
                List.of(OrderItemDto.of(1L, 1L, 1000L))
        );
    }

    private AdminProductDto createAdminProductDto(String productName){
        return AdminProductDto.of(
                1L,
                createUserAccountDto(),
                createCategoryDto("사무용품"),
                createSupplierDto("공급업체1"),
                productName,
                100L,
                1000L,
                1000L,
                100L
        );
    }

    private Category createCategory(){
        return Category.of(
                1L,
                "물류"
        );
    }

    private CategoryDto createCategoryDto(String categoryName){
        return CategoryDto.of(
                1L,
                categoryName
        );
    }

    private Supplier createSupplier(){
        return Supplier.of(
                1L,
                "공급업체1",
                "A12"
        );
    }

    private SupplierDto createSupplierDto(String supplierName){
        return SupplierDto.of(
                1L,
                supplierName,
                "A12"
        );
    }
}
