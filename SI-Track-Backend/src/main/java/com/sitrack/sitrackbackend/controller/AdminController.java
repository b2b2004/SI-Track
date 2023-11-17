package com.sitrack.sitrackbackend.controller;

import com.sitrack.sitrackbackend.config.security.auth.PrincipalDetails;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.AdminProductDto;
import com.sitrack.sitrackbackend.dto.OrderDto;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import com.sitrack.sitrackbackend.service.AdminService;
import com.sitrack.sitrackbackend.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {

    private final AdminService adminService;

    // ADMIN, MANAGER은 모든 유저 정보 확인 가능
    @GetMapping("/allUser")
    public ResponseEntity<?> findAllUser(){
        List<UserAccountDto> userAccountList = adminService.findAllUsers();
        return new ResponseEntity<>(userAccountList, HttpStatus.OK);
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

    // 상품 재고 업데이트
    @PostMapping("/update/product")
    public ResponseEntity<?> updateProduct(@RequestBody AdminProductDto productDto){
        String msg = adminService.updateProduct(productDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // 주문 현황 업데이트
    @PostMapping("/update/order")
    public ResponseEntity<?> updateOrder(@RequestBody OrderDto orderDto){
        String msg = adminService.updateOrder(orderDto);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

}
