package com.sitrack.sitrackbackend.controller;

import com.sitrack.sitrackbackend.config.security.auth.PrincipalDetails;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.request.CartItemRequest;
import com.sitrack.sitrackbackend.dto.response.CartItemResponse;
import com.sitrack.sitrackbackend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/cart")
@Controller
public class CartController {

    private final CartService cartService;

    @GetMapping("/list")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal PrincipalDetails principalDetails){
        UserAccount user = principalDetails.getUser();
        List<CartItemResponse> cartItemResponses =  cartService.findAllCart(user);
        return new ResponseEntity<>(cartItemResponses, HttpStatus.OK);
    }

    // 장바구니 상품 추가
    @PostMapping("/addCart")
    public ResponseEntity<?> addCart(@Valid @RequestBody CartItemRequest cartItemRequest,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails){
        UserAccount user = principalDetails.getUser();
        cartService.createCart(cartItemRequest, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 장바구니 상품 삭제
    @PostMapping("/{cartItemId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long cartItemId,
                                           @AuthenticationPrincipal PrincipalDetails principalDetails){
        cartService.deleteOneCart(cartItemId, principalDetails.getUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
