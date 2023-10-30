package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.domain.Cart;
import com.sitrack.sitrackbackend.domain.CartItem;
import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.domain.constant.ProductImageType;
import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.request.CartItemRequest;
import com.sitrack.sitrackbackend.dto.response.CartItemResponse;
import com.sitrack.sitrackbackend.repository.CartItemRepository;
import com.sitrack.sitrackbackend.repository.CartRepository;
import com.sitrack.sitrackbackend.repository.ProductRepository;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserAccountRepository userAccountRepository;

    // 장바구니 생성
    public void createCart(CartItemRequest cartItemRequest, UserAccount user){
        // 상품 id로 상품 가져오고
        Product product = productRepository.findById(cartItemRequest.productId()).orElseThrow();

        // 유저 아이디로 카트 있는지 확인하고
        Cart cart = cartRepository.findCartByUserAccount(user);

        // 장바구니 없으면 만들어주고
        if (cart == null) {
            Cart newCart = new Cart(user);
            cartRepository.save(newCart);
        }

        cart = cartRepository.findCartByUserAccount(user);
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());

        if(cartItem == null){   // 상품이 존재 하지 않을 시
            cartItem = CartItem.of(cart, product, cartItemRequest.quantity());
            cart.addcartItem(cartItem);
        }else{  // 상품이 존재 할 시 수량만 증가
            cartItem.addQuantity(cartItemRequest.quantity());
        }

        cartRepository.save(cart);
        cartItemRepository.save(cartItem);
    }

    // 장바구니 상품 삭제
    public void deleteOneCart(Long id, UserAccount user){

        CartItem cartItem = cartItemRepository.findById(id).orElseThrow();
        Cart cart = cartItem.getCart();

        if(!cart.getUserAccount().equals(user)){
            System.out.println("아이디가 달라서 삭제 실패");
            return;
        }

        cartItemRepository.delete(cartItem);
    }

    // 장바구니 조회
    public List<CartItemResponse> findAllCart(UserAccount user){
        user.getCart().getCartItems();

        Cart cart = cartRepository.findCartByUserAccount(user);
        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
        List<CartItemResponse> result = new ArrayList<>();

        for(CartItem item : items){
            CartItemResponse cartItemResponse = CartItemResponse.toDto(item, item.getQuantity());
            result.add(cartItemResponse);
        }

        return result;
    }

}
