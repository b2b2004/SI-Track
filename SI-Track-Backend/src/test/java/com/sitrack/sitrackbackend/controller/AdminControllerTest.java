package com.sitrack.sitrackbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitrack.sitrackbackend.annotation.WithAuthUser;
import com.sitrack.sitrackbackend.domain.Category;
import com.sitrack.sitrackbackend.domain.Supplier;
import com.sitrack.sitrackbackend.domain.constant.RoleType;
import com.sitrack.sitrackbackend.dto.*;
import com.sitrack.sitrackbackend.service.AdminService;
import com.sitrack.sitrackbackend.service.PaginationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private AdminService adminService;

    @MockBean
    private PaginationService paginationService;

    public AdminControllerTest(@Autowired MockMvc mvc,
                                 @Autowired ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @DisplayName("[AdminC] 유저 리스트 출력")
    @WithAuthUser(userId = "test1", role = "ROLE_ADMIN")
    @Test
    public void findAll_users() throws Exception {
        // Given
        given(adminService.findUsersWithSearchType(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        // When & Then
        mvc.perform(
                        get("/admin/allUser"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$..users").exists())
                .andExpect(jsonPath("$..barNumbers").exists());
        then(adminService).should().findUsersWithSearchType(eq(null), eq(null), any(Pageable.class));
    }

    @DisplayName("[AdminC] 상품 리스트 출력")
    @WithAuthUser(userId = "test1", role = "ROLE_ADMIN")
    @Test
    public void findAll_products() throws Exception {
        // Given
        given(adminService.findProductsWithSearchType(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        // When & Then
        mvc.perform(
                        get("/admin/allProduct"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$..products").exists())
                .andExpect(jsonPath("$..barNumbers").exists());
        then(adminService).should().findProductsWithSearchType(eq(null), eq(null), any(Pageable.class));
    }

    @DisplayName("[AdminC] 주문 리스트 출력")
    @WithAuthUser(userId = "test1", role = "ROLE_ADMIN")
    @Test
    public void findAll_orders() throws Exception {
        // Given
        given(adminService.findOrdersWithSearchType(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        // When & Then
        mvc.perform(
                        get("/admin/allOrder"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$..orders").exists())
                .andExpect(jsonPath("$..barNumbers").exists());
        then(adminService).should().findOrdersWithSearchType(eq(null), eq(null), any(Pageable.class));
    }

    @DisplayName("[AdminC] 공급업체 리스트 출력")
    @WithAuthUser(userId = "test1", role = "ROLE_ADMIN")
    @Test
    public void findAll_suppliers() throws Exception {
        // Given
        given(adminService.findSuppliersWithSearchType(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        // When & Then
        mvc.perform(
                        get("/admin/allSupplier"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$..suppliers").exists())
                .andExpect(jsonPath("$..barNumbers").exists());
        then(adminService).should().findSuppliersWithSearchType(eq(null), eq(null), any(Pageable.class));
    }

    @DisplayName("[AdminC] 카테고리 리스트 출력")
    @WithAuthUser(userId = "test1", role = "ROLE_ADMIN")
    @Test
    public void findAll_categorys() throws Exception {
        // Given
        List<CategoryDto> categoryList = List.of(createCategoryDto("사무용품"), createCategoryDto("마우스"));
        given(adminService.findAllCategory()).willReturn(categoryList);

        // When & Then
        mvc.perform(
                        get("/admin/allCategory"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$..categoryId").exists())
                .andExpect(jsonPath("$..categoryName").exists());
        then(adminService).should().findAllCategory();
    }

    @DisplayName("[AdminC] 상품 업데이트 성공")
    @WithAuthUser(userId = "test1", role = "ROLE_ADMIN")
    @Test
    public void update_product() throws Exception {
        // Given
        AdminProductDto adminProductDto = createAdminProductDto("볼펜");

        // When & Then
        mvc.perform(
                post("/admin/update/product")
                        .content(objectMapper.writeValueAsString(adminProductDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andExpect(status().isOk());
        then(adminService).should().updateProduct(any());
    }

    @DisplayName("[AdminC] 주문 현황 업데이트 성공")
    @WithAuthUser(userId = "test1", role = "ROLE_ADMIN")
    @Test
    public void update_order_status() throws Exception {
        // Given
        OrderDto orderDto = createOrderDto("테스터");

        // When & Then
        mvc.perform(
                post("/admin/update/order")
                        .content(objectMapper.writeValueAsString(orderDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andExpect(status().isOk());
        then(adminService).should().updateOrder(any());
    }

    @DisplayName("[AdminC] 공급업체 등록 성공")
    @WithAuthUser(userId = "test1", role = "ROLE_ADMIN")
    @Test
    public void register_supplier() throws Exception {
        // Given
        SupplierDto supplierDto = createSupplierDto("공급업체1");

        // When & Then
        mvc.perform(
                post("/admin/register/supplier")
                        .content(objectMapper.writeValueAsString(supplierDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andExpect(status().isOk());
        then(adminService).should().registerSupplier(any());
    }

    @DisplayName("[AdminC] 카테고리 등록 성공")
    @WithAuthUser(userId = "test1", role = "ROLE_ADMIN")
    @Test
    public void register_category() throws Exception {
        // Given
        CategoryDto categoryDto = createCategoryDto("사무용품");

        // When & Then
        mvc.perform(
                post("/admin/register/category")
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andExpect(status().isOk());
        then(adminService).should().registerCategory(any());
    }

    private UserAccountDto createUserAccountDto(String userId) {
        return UserAccountDto.of(
                userId,
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

    private AdminProductDto createAdminProductDto(String productName){
        return AdminProductDto.of(
                1L,
                createUserAccountDto("test1"),
                createCategoryDto("물류"),
                createSupplierDto("공급업체1"),
                productName,
                100L,
                1000L,
                1000L,
                100L
        );
    }

    private OrderDto createOrderDto(String recipient){
        return OrderDto.of(
                1L,
                "test1",
                1000L,
                "우리집",
                "주문 준비",
                "조심히 배송",
                recipient,
                "010-1111-2222",
                LocalDateTime.now(),
                List.of(OrderItemDto.of(1L, 1L, 1000L))
        );
    }

    private CategoryDto createCategoryDto(String categoryName){
        return CategoryDto.of(
                1L,
                categoryName
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
