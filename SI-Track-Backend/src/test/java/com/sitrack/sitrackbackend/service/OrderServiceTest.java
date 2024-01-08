package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.Exception.CustomException;
import com.sitrack.sitrackbackend.domain.*;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.OrderItemDto;
import com.sitrack.sitrackbackend.dto.request.OrderRequest;
import com.sitrack.sitrackbackend.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService sut;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private UserAccountRepository userAccountRepository;


    @DisplayName("[OrderS] 상품 수량이 없을때 주문 실패")
    @Test
    public void save_have_noQuantity(){
        // Given
        UserAccount userAccount = createUserAccount("test1");
        OrderRequest orderRequest = createOrderRequest();
        Product product = createProduct(1L);
        product.setProductStockQuantity(1L);

        given(productRepository.findById(any())).willReturn(Optional.of(product));

        // When
        Throwable t = catchThrowable(() -> sut.save(orderRequest, userAccount));

        System.out.println(t.toString());
        // Then
        assertThat(t).isInstanceOf(CustomException.class);
    }

    @DisplayName("[OrderS] 장바구니에 상품이 없을때 주문 성공")
    @Test
    public void save_have_noCart_order(){
        // Given
        UserAccount userAccount = createUserAccount("test1");
        OrderRequest orderRequest = createOrderRequest();
        Product product = createProduct(1L);

        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(cartRepository.findCartByUserAccount(any())).willReturn(Optional.empty());

        // Then
        sut.save(orderRequest, userAccount);

        // When
        then(orderRepository).should().save(any());
    }

    @DisplayName("[OrderS] 장바구니에 상품이 있을때 주문 성공")
    @Test
    public void save_have_Cart_order(){
        // Given
        UserAccount userAccount = createUserAccount("test1");
        OrderRequest orderRequest = createOrderRequest();
        Product product = createProduct(1L);
        Cart cart = createCart(1L);
        List<CartItem> cartItems = createCartItem();
        userAccount.setCart(cart);

        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(cartRepository.findCartByUserAccount(any())).willReturn(Optional.of(cart));
        given(cartItemRepository.findByCartId(any())).willReturn(cartItems);

        // Then
        sut.save(orderRequest, userAccount);

        // When
        then(orderRepository).should().save(any());
        then(cartItemRepository).should().delete(any());
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

    private Product createProduct(Long id){
        Product product = Product.of(
                createUserAccount("test1"),
                createCategory(),
                createSupplier(),
                "볼펜",
                100L,
                1000L,
                "볼펜이다",
                1000L,
                100L
        );
        ReflectionTestUtils.setField(product, "id", id);
        return product;
    }

    private OrderRequest createOrderRequest(){
        return OrderRequest.of(
                20000L,
                "서울시",
                "조심히 배송",
                "권용호",
                "010-1111-2222",
                List.of(
                        new OrderItemDto(1L, 2L, 1000L)
                )
        );
    }

    private List<CartItem> createCartItem(){
        return List.of(
                CartItem.of(createCart(1L), createProduct(1L), 2L)
        );
    }

    private Cart createCart(Long id){
        Cart cart = Cart.of(
                createUserAccount("test1"),
                1L
        );
        ReflectionTestUtils.setField(cart, "id", id);

        return cart;
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
