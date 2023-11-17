package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.domain.Order;
import com.sitrack.sitrackbackend.domain.OrderItem;
import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.domain.constant.RoleType;
import com.sitrack.sitrackbackend.dto.AdminProductDto;
import com.sitrack.sitrackbackend.dto.OrderDto;
import com.sitrack.sitrackbackend.dto.OrderItemDto;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import com.sitrack.sitrackbackend.repository.OrderRepository;
import com.sitrack.sitrackbackend.repository.ProductRepository;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @InjectMocks
    private AdminService sut;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Test
    @DisplayName("[AdminS] 모든 유저내역 확인")
    public void findAll_user_success() {
        // Given
        List<UserAccount> userAccountList = List.of(createUserAccount("test1"), createUserAccount("test2"), createUserAccount("test3"));
        given(userAccountRepository.findAll()).willReturn(userAccountList);

        // When
        List<UserAccountDto> userAccountDtoList = sut.findAllUsers();

        // Then
        assertThat(userAccountDtoList)
                .hasSize(3)
                .first().hasFieldOrPropertyWithValue("userId", userAccountList.get(0).getUserId());
        then(userAccountRepository).should().findAll();
    }

    @Test
    @DisplayName("[AdminS] 모든 상품 확인")
    public void findAll_product_success() {
        // Given
        List<Product> productList = List.of(createProduct("볼펜1"), createProduct("볼펜2"), createProduct("볼펜3"));
        given(productRepository.findAll()).willReturn(productList);

        // When
        List<AdminProductDto> adminProductDtoList = sut.findAllProducts();

        // Then
        assertThat(adminProductDtoList)
                .hasSize(3)
                .first().hasFieldOrPropertyWithValue("productName", productList.get(0).getProductName());
        then(productRepository).should().findAll();
    }

    @Test
    @DisplayName("[AdminS] 모든 주문 확인")
    public void findAll_order_success() {
        // Given
        List<Order> orderList = List.of(createOrder("테스터1"), createOrder("테스터2"), createOrder("테스터3"));
        given(orderRepository.findAll()).willReturn(orderList);

        // When
        List<OrderDto> orderDtoList = sut.findAllOrders();

        // Then
        assertThat(orderDtoList)
                .hasSize(3)
                .first().hasFieldOrPropertyWithValue("recipient", orderList.get(0).getRecipient());
        then(orderRepository).should().findAll();
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
                1L,
                "A12",
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
                1L,
                "A12",
                productName,
                100L,
                1000L,
                1000L,
                100L
        );
    }
}
