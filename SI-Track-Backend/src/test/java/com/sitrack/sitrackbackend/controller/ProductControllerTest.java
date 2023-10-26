package com.sitrack.sitrackbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitrack.sitrackbackend.annotation.WithAuthUser;
import com.sitrack.sitrackbackend.dto.ProductDto;
import com.sitrack.sitrackbackend.dto.request.ProductRequest;
import com.sitrack.sitrackbackend.service.PaginationService;
import com.sitrack.sitrackbackend.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class) // 해당 컨트롤러만 테스트
public class ProductControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private PaginationService paginationService;

    public ProductControllerTest(@Autowired MockMvc mvc,
                                 @Autowired ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @DisplayName("[ProductC] 상품 리스트 페이지 - 정상 호출")
    @WithMockUser
    @Test
    public void product_List() throws Exception {
        // Given
        given(productService.findbyId_product_all_and_search(any(Pageable.class), eq(null))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        // When & Then
        mvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$.barNumbers").exists())
                .andExpect(jsonPath("$.products").exists());
        then(productService).should().findbyId_product_all_and_search(any(Pageable.class), eq(null));
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @DisplayName("[ProductC] 상품 리스트 페이지 - 검색어와 함께 호출")
    @WithMockUser
    @Test
    public void product_List_with_search() throws Exception {
        // Given
        String searchValue = "title";
        given(productService.findbyId_product_all_and_search(any(Pageable.class), eq(searchValue))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        // When & Then
        mvc.perform(get("/product/list")
                        .queryParam("searchValue", searchValue))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$.barNumbers").exists())
                .andExpect(jsonPath("$.products").exists());
        then(productService).should().findbyId_product_all_and_search(any(Pageable.class), eq(searchValue));
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @DisplayName("[ProductC] 상품 등록 - 정상 호출")
    @WithAuthUser(userId = "kwon", role = "ROLE_USER")
    @Test
    public void product_new_register() throws Exception {
        // Given
        given(productService.register_product(any(ProductDto.class), any())).willReturn("상품 등록 완료");

        MockMultipartFile image1 = new MockMultipartFile("productImages", "test.txt", "text/plain", "test file".getBytes(StandardCharsets.UTF_8) );
        MockMultipartFile image2 = new MockMultipartFile("productImages", "test2.txt", "text/plain", "test file2".getBytes(StandardCharsets.UTF_8) );
        ProductRequest productRequestImp = createProductRequest();
        String pRDtoJson = objectMapper.writeValueAsString(productRequestImp);
        MockMultipartFile productRequest = new MockMultipartFile("productRequest", "productRequest", "application/json", pRDtoJson.getBytes(StandardCharsets.UTF_8));

        mvc.perform(
                multipart("/product/register")
                        .file(image1)
                        .file(image2)
                        .file(productRequest)
                        .with(csrf()))
                .andExpect(status().isOk());

        then(productService).should().register_product(any(ProductDto.class), any());
    }


    private ProductRequest createProductRequest(){
        return ProductRequest.of(
                1L,
                "A12",
                "종이",
                100L,
                1000L,
                 "종이다",
                10L,
                5L,
                "test1"
        );
    }

}
