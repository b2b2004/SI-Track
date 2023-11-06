package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.domain.Cart;
import com.sitrack.sitrackbackend.domain.CartItem;
import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.request.CartItemRequest;
import com.sitrack.sitrackbackend.dto.response.CartItemResponse;
import com.sitrack.sitrackbackend.repository.CartItemRepository;
import com.sitrack.sitrackbackend.repository.CartRepository;
import com.sitrack.sitrackbackend.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    CartService sut;

    @Mock
    CartRepository cartRepository;

    @Mock
    CartItemRepository cartItemRepository;

    @Mock
    ProductRepository productRepository;

    @Test
    @DisplayName("[CartS] 장바구니 등록 테스트")
    public void create_Cart(){
        // Given
        CartItemRequest cartItemRequest = createCartItemRequest();
        UserAccount user = createUserAccount("test1");
        Product product = createProduct();
        Cart cart = createCart();

        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(cartRepository.findCartByUserAccount(any())).willReturn(Optional.of(cart));
        given(cartItemRepository.findByCartIdAndProductId(any(), any())).willReturn(null);

        // When
        sut.createCart(cartItemRequest, user);

        // Then
        then(cartRepository).should().save(any());
    }

    @Test
    @DisplayName("[CartS] 장바구니에 이미 같은 상품이 있을때 수량증가")
    public void create_haveItem_Cart(){
        // Given
        CartItemRequest cartItemRequest = createCartItemRequest();
        UserAccount user = createUserAccount("test1");
        Product product = createProduct();
        Cart cart = createCart();
        CartItem cartItem = createCartItem();

        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(cartRepository.findCartByUserAccount(any())).willReturn(Optional.of(cart));
        given(cartItemRepository.findByCartIdAndProductId(any(), any())).willReturn(cartItem);

        // When
        sut.createCart(cartItemRequest, user);

        // Then
        then(cartRepository).should().save(any());
    }

    @Test
    @DisplayName("[CartS] 장바구니 상품 삭제")
    public void delete_one_Cart(){
        // Given
        UserAccount user = createUserAccount("test1");
        CartItem cartItem = createCartItem();

        given(cartItemRepository.findById(any())).willReturn(Optional.of(cartItem));
        // When
        sut.deleteOneCart(1L, user);

        // Then
        then(cartItemRepository).should().delete(any());
    }

    @Test
    @DisplayName("[CartS] 회원 장바구니 조회")
    public void find_All_Cart(){
        // Given
        UserAccount user = createUserAccount("test1");
        Cart cart = createCart();
        CartItem cartItem = createCartItem();

        given(cartRepository.findCartByUserAccount(any())).willReturn(Optional.of(cart));
        given(cartItemRepository.findByCartId(any())).willReturn(List.of(cartItem));

        // When
        List<CartItemResponse> result = sut.findAllCart(user);

        // Then
        assertThat(result.size()).isEqualTo(1);
    }

    public CartItemRequest createCartItemRequest(){
        return CartItemRequest.of(
                1L,
                2L
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

    private Product createProduct(){
        return Product.of(
                createUserAccount("test1"),
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

    private Cart createCart(){
        return Cart.of(
                createUserAccount("test1"),
                1L
        );
    }

    private CartItem createCartItem(){
        return CartItem.of(
                createCart(),
                createProduct(),
                2L
        );
    }


}
