package com.sitrack.sitrackbackend.controller;

import com.sitrack.sitrackbackend.config.security.auth.PrincipalDetails;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.request.OrderRequest;
import com.sitrack.sitrackbackend.dto.response.OrderResponse;
import com.sitrack.sitrackbackend.service.OrderService;
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

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/order")
@Controller
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/page")
    public ResponseEntity<?> order_Page(@RequestBody OrderRequest orderRequest,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails){
        UserAccount userAccount = principalDetails.getUser();
        orderService.save(orderRequest, userAccount);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> order_view_all(@AuthenticationPrincipal PrincipalDetails principalDetails){
        UserAccount userAccount = principalDetails.getUser();
        List<OrderResponse> orderResponses = orderService.findByUserOrders(userAccount);
        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
    }

}
