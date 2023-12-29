package com.sitrack.sitrackbackend.controller;

import com.sitrack.sitrackbackend.dto.*;
import com.sitrack.sitrackbackend.service.AdminService;
import com.sitrack.sitrackbackend.service.PaginationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
@Api(tags = "Admin Controller")
public class AdminController {

    private final AdminService adminService;
    private final PaginationService paginationService;

    // ADMIN, MANAGER은 모든 유저 정보 확인 가능
    @ApiOperation(value = "모든 유저 조회(검색 및 페이징)", notes = "searchType, searchValue가 없을 시 전체 조회")
    @GetMapping("/allUser")
    public ResponseEntity<?> find_user_all_and_search(@RequestParam(required = false) String searchType,
                                                @RequestParam(required = false) String searchValue,
                                                @PageableDefault(size = 12, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        Page<UserAccountDto> userAccounts = adminService.findUsersWithSearchType(searchType, searchValue, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), userAccounts.getTotalPages());
        Map<String, Object> result = new HashMap<>();
        result.put("users" , userAccounts);
        result.put("barNumbers", barNumbers);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // ADMIN, MANAGER은 모든 상품 정보 확인 가능
    @ApiOperation(value = "모든 상품 조회(검색 및 페이징)", notes = "searchType, searchValue가 없을 시 전체 조회")
    @GetMapping("/allProduct")
    public ResponseEntity<?> findAllProduct(@RequestParam(required = false) String searchType,
                                            @RequestParam(required = false) String searchValue,
                                            @PageableDefault(size = 12, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        Page<AdminProductDto> products = adminService.findProductsWithSearchType(searchType, searchValue, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), products.getTotalPages());
        Map<String, Object> result = new HashMap<>();
        result.put("products" , products);
        result.put("barNumbers", barNumbers);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // ADMIN, MANAGER은 모든 주문 정보 확인 가능
    @ApiOperation(value = "모든 주문 조회(검색 및 페이징)", notes = "searchType, searchValue가 없을 시 전체 조회")
    @GetMapping("/allOrder")
    public ResponseEntity<?> findAllOrder(@RequestParam(required = false) String searchType,
                                          @RequestParam(required = false) String searchValue,
                                          @PageableDefault(size = 12, sort = "orderId", direction = Sort.Direction.DESC) Pageable pageable){
        Page<OrderDto> orders = adminService.findOrdersWithSearchType(searchType, searchValue, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), orders.getTotalPages());
        Map<String, Object> result = new HashMap<>();
        result.put("orders" , orders);
        result.put("barNumbers", barNumbers);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "모든 공급업체 조회(검색 및 페이징)", notes = "searchType, searchValue가 없을 시 전체 조회")
    @GetMapping("/allSupplier")
    public ResponseEntity<?> findAllSupplier(@RequestParam(required = false) String searchType,
                                             @RequestParam(required = false) String searchValue,
                                             @PageableDefault(size = 12, sort = "supplierId", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SupplierDto> suppliers = adminService.findSuppliersWithSearchType(searchType, searchValue, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), suppliers.getTotalPages());
        Map<String, Object> result = new HashMap<>();
        result.put("suppliers" , suppliers);
        result.put("barNumbers", barNumbers);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "모든 카테고리 조회", notes = "카테고리 전체 조회")
    @GetMapping("/allCategory")
    public ResponseEntity<?> findAllCategory(){
        List<CategoryDto> categoryList = adminService.findAllCategory();
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    // 상품 재고 업데이트
    @ApiOperation(value = "상품 재고 업데이트", notes = "재고만 업데이트")
    @PostMapping("/update/product")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody AdminProductDto productDto){
        String msg = adminService.updateProduct(productDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // 주문 현황 업데이트
    @ApiOperation(value = "주문 현황 업데이트", notes = "배송, 대기 처리 가능")
    @PostMapping("/update/order")
    public ResponseEntity<?> updateOrder(@RequestBody OrderDto orderDto){
        String msg = adminService.updateOrder(orderDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @ApiOperation(value = "공급 업체 등록", notes = "공급 업체 등록")
    @PostMapping("/register/supplier")
    public ResponseEntity<?> registerSupplier(@Valid @RequestBody SupplierDto supplierDto){
        String msg = adminService.registerSupplier(supplierDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @ApiOperation(value = "카테고리 등록", notes = "카테고리 등록")
    @PostMapping("/register/category")
    public ResponseEntity<?> registerCategory(@Valid @RequestBody CategoryDto categoryDto){
        String msg = adminService.registerCategory(categoryDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @ApiOperation(value = "공급업체 수정", notes = "공급업체 수정")
    @PostMapping("/update/supplier")
    public ResponseEntity<?> updateSupplier(@Valid @RequestBody SupplierDto supplierDto){
        String msg = adminService.updateSupplier(supplierDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @ApiOperation(value = "카테고리 수정", notes = "카테고리 수정")
    @PostMapping("/update/category")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDto categoryDto){
        String msg = adminService.updateCategory(categoryDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @ApiOperation(value = "공급업체 삭제", notes = "공급업체 삭제")
    @DeleteMapping("/delete/supplier/{supplierId}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long supplierId){
        String msg = adminService.deleteSupplier(supplierId);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @ApiOperation(value = "카테고리 삭제", notes = "카테고리 삭제")
    @DeleteMapping("/delete/category/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
        String msg = adminService.deleteCategory(categoryId);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

}
