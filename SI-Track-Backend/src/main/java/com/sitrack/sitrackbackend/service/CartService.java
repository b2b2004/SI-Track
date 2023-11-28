package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.Exception.CustomException;
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

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.sitrack.sitrackbackend.Exception.ErrorCode.CART_NOT_FOUND;

@RequiredArgsConstructor
@Transactional
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    // 장바구니 생성
    public void createCart(CartItemRequest cartItemRequest, UserAccount user){

        // 상품 id로 상품 가져오고
        Product product = productRepository.findById(cartItemRequest.productId()).orElseThrow();

        // 장바구니 없으면 만들어주고
        if (!cartRepository.findCartByUserAccount(user).isPresent()) {
            Cart newCart = new Cart(user);
            cartRepository.save(newCart);
        }

        Cart cart = cartRepository.findCartByUserAccount(user)
                .orElseThrow(()-> new CustomException(CART_NOT_FOUND));
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());


        if(cartItem == null){   // 상품이 존재 하지 않을 시
            cartItem = CartItem.of(cart, product, cartItemRequest.quantity());
            cart.addcartItem(cartItem);
        }else{  // 상품 존재 시
            List<CartItem> cartItems =  cart.getCartItems();
            for(CartItem item : cartItems){
                if(item.equals(cartItem)){
                    item.addQuantity(cartItemRequest.quantity());
                }
            }
        }

        cartRepository.save(cart);
    }

    // 장바구니 상품 삭제
    public void deleteOneCart(Long id, UserAccount user){

        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(()-> new CustomException(CART_NOT_FOUND));
        Cart cart = cartItem.getCart();

        if(!cart.getUserAccount().equals(user)){
            System.out.println("아이디가 달라서 삭제 실패");
            return;
        }

        cartItemRepository.delete(cartItem);
    }

    // 장바구니 조회
    public List<CartItemResponse> findAllCart(UserAccount user){

        Cart cart = cartRepository.findCartByUserAccount(user)
                .orElseThrow(()-> new CustomException(CART_NOT_FOUND));
        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
        List<CartItemResponse> result = new ArrayList<>();

        for(CartItem item : items){
            CartItemResponse cartItemResponse = CartItemResponse.toDto(item, item.getQuantity());
            result.add(cartItemResponse);
        }

        return result;
    }

}
