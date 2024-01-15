package com.sitrack.sitrackbackend.controller;

import com.sitrack.sitrackbackend.config.security.auth.PrincipalDetails;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.request.OrderRequest;
import com.sitrack.sitrackbackend.dto.response.OrderResponse;
import com.sitrack.sitrackbackend.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/order")
@Controller
@Api(tags = "Order Controller")
public class OrderController {

    private final OrderService orderService;

    @ApiOperation(value = "해당 유저의 주문 추가", notes = "주문 페이지에서 주문")
    @PostMapping("/page")
    public ResponseEntity<?> order_Page(@Valid @RequestBody OrderRequest orderRequest,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails){
        UserAccount userAccount = principalDetails.getUser();
        orderService.save(orderRequest, userAccount);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @ApiOperation(value = "해당 유저의 주문 조회", notes = "유저의 모든 주문 조회")
    @GetMapping("/all")
    public ResponseEntity<?> order_view_all(@AuthenticationPrincipal PrincipalDetails principalDetails){
        UserAccount userAccount = principalDetails.getUser();
        List<OrderResponse> orderResponses = orderService.findByUserOrders(userAccount);
        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
    }

}
