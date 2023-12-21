package com.sitrack.sitrackbackend.controller;

import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.*;
import com.sitrack.sitrackbackend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {

    private final AdminService adminService;

    // ADMIN, MANAGER은 모든 유저 정보 확인 가능
    @GetMapping("/allUser")
    public ResponseEntity<?> findWithSearchType(@RequestParam(required = false) String searchType,
                                         @RequestParam(required = false) String searchValue){
        System.out.println("SearchType: " + searchType + " // searchValue :" + searchValue);
        List<UserAccountDto> userAccounts = adminService.findUserWithSearchType(searchType, searchValue);
        return new ResponseEntity<>(userAccounts, HttpStatus.OK);
    }

    // ADMIN, MANAGER은 모든 상품 정보 확인 가능
    @GetMapping("/allProduct")
    public ResponseEntity<?> findAllProduct(){
        List<AdminProductDto> productList = adminService.findAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    // ADMIN, MANAGER은 모든 주문 정보 확인 가능
    @GetMapping("/allOrder")
    public ResponseEntity<?> findAllOrder(){
        List<OrderDto> orderList = adminService.findAllOrders();
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @GetMapping("/allSupplier")
    public ResponseEntity<?> findAllSupplier(){
        List<SupplierDto> supplierList = adminService.findAllSupplier();
        return new ResponseEntity<>(supplierList, HttpStatus.OK);
    }

    @GetMapping("/allCategory")
    public ResponseEntity<?> findAllCategory(){
        List<CategoryDto> categoryList = adminService.findAllCategory();
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    // 상품 재고 업데이트
    @PostMapping("/update/product")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody AdminProductDto productDto){
        String msg = adminService.updateProduct(productDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // 주문 현황 업데이트
    @PostMapping("/update/order")
    public ResponseEntity<?> updateOrder(@RequestBody OrderDto orderDto){
        String msg = adminService.updateOrder(orderDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/register/supplier")
    public ResponseEntity<?> registerSupplier(@Valid @RequestBody SupplierDto supplierDto){
        String msg = adminService.registerSupplier(supplierDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/register/category")
    public ResponseEntity<?> registerCategory(@Valid @RequestBody CategoryDto categoryDto){
        String msg = adminService.registerCategory(categoryDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/update/supplier")
    public ResponseEntity<?> updateSupplier(@Valid @RequestBody SupplierDto supplierDto){
        String msg = adminService.updateSupplier(supplierDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/update/category")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDto categoryDto){
        String msg = adminService.updateCategory(categoryDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping("/delete/supplier/{supplierId}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long supplierId){
        String msg = adminService.deleteSupplier(supplierId);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping("/delete/category/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
        String msg = adminService.deleteCategory(categoryId);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

}
