package com.sitrack.sitrackbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitrack.sitrackbackend.annotation.WithAuthUser;
import com.sitrack.sitrackbackend.dto.request.CartItemRequest;
import com.sitrack.sitrackbackend.dto.response.CartItemResponse;
import com.sitrack.sitrackbackend.service.CartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private CartService cartService;


    public CartControllerTest(@Autowired MockMvc mvc,
                              @Autowired ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }


    @DisplayName("[CartC] 장바구니 상품 등록")
    @WithAuthUser(userId = "test1", role = "ROLE_USER")
    @Test
    public void add_product() throws Exception {
        // Given
        CartItemRequest cartItemRequest = createCartItemRequest();

        // When & Then
        mvc.perform(
                post("/cart/addCart")
                        .content(objectMapper.writeValueAsString(cartItemRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andExpect(status().isOk());
        then(cartService).should().createCart(any(), any());

    }

    @DisplayName("[CartC] 장바구니 리스트 조회")
    @WithAuthUser(userId = "test1", role = "ROLE_USER")
    @Test
    public void find_all() throws Exception {
        // Given
        CartItemResponse cartItemResponse = createCartItemResponse();
        given(cartService.findAllCart(any())).willReturn(List.of(cartItemResponse));

        // When & Then
        mvc.perform(
                get("/cart/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$..cartItemId").exists())
                .andExpect(jsonPath("$..productName").exists())
                .andExpect(jsonPath("$..productDetail").exists())
                .andExpect(jsonPath("$..productPrice").exists());
    }

    @DisplayName("[CartC] 장바구니 상품 삭제")
    @WithAuthUser(userId = "test1", role = "ROLE_USER")
    @Test
    public void delete_cart() throws Exception {
        mvc.perform(
                post("/cart/1")
                        .with(csrf()))
                .andExpect(status().isOk());
        then(cartService).should().deleteOneCart(any(), any());
    }

    public CartItemRequest createCartItemRequest(){
        return CartItemRequest.of(
                1L,
                2L
        );
    }

    public CartItemResponse createCartItemResponse(){
        return CartItemResponse.of(
                1L,
                1L,
                "종이",
                "종이다",
                1000L,
                "image",
                1L
        );
    }

}
