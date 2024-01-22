package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.Exception.CustomException;
import com.sitrack.sitrackbackend.Exception.ErrorCode;
import com.sitrack.sitrackbackend.domain.*;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.OrderItemDto;
import com.sitrack.sitrackbackend.dto.OrderItemListDto;
import com.sitrack.sitrackbackend.dto.request.OrderRequest;
import com.sitrack.sitrackbackend.dto.response.OrderResponse;
import com.sitrack.sitrackbackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sitrack.sitrackbackend.Exception.ErrorCode.*;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepositoryCustom productRepositoryCustom;
    private final CartItemRepositoryCustom cartItemRepositoryCustom;
    private final UserAccountRepositoryCustom userAccountRepositoryCustom;

    @Transactional
    public void save(OrderRequest orderRequest, UserAccount userAccount) {

        List<OrderItemDto> orderItemDto = orderRequest.orderItemDtos();
        List<OrderItem> orderItems = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        try {

            for(OrderItemDto dto : orderItemDto){
                Product product = productRepositoryCustom.findById(dto.productId())
                        .orElseThrow(()-> new CustomException(PRODUCT_NOT_FOUND));
                OrderItem orderItem = OrderItem.of(product, dto.quantity(), product.getProductPrice());
                orderItems.add(orderItem);
                ids.add(dto.productId());
            }

            /**
             * 재고 확인 및
             * 주문 수 만큼 재고 마이너스
             */
            for(OrderItem orderItem : orderItems){
                if(orderItem.getProduct().getProductStockQuantity() < orderItem.getOrderItemQuantity()){
                    throw new CustomException(OUT_OF_STOCK);
                }else{
                    orderItem.getProduct().minusproductStockQuantity(orderItem.getOrderItemQuantity());
                }
            }

            Order order = Order.of(userAccount, orderRequest, orderItems);
            orderRepository.save(order);

            /**
             * 장바구니에 있는 주문 상품 제거
             */
            if(!ids.isEmpty()){
                cartItemRepositoryCustom.deleteAllByIds(ids);
            }

        }catch (CustomException e){
            e.printStackTrace();
            throw new CustomException(e.getErrorCode());
        }

    }

    public List<OrderResponse> findByUserOrders(UserAccount userAccount) {
        UserAccount user = userAccountRepositoryCustom.findByUserId(userAccount.getUserId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        List<Order> orders = user.getOrders();
        List<OrderResponse> orderResponses = new ArrayList<>();

        for(Order order : orders){
            List<OrderItemListDto> orderItemListDtos = new ArrayList<>();
            for(OrderItem orderItem : order.getOrderItems()){
                OrderItemListDto orderItemListDto = OrderItemListDto.of(orderItem.getProduct(), orderItem.getOrderItemQuantity());
                orderItemListDtos.add(orderItemListDto);
            }
            OrderResponse orderResponse = OrderResponse.of(order,orderItemListDtos);
            orderResponses.add(orderResponse);
        }

        return orderResponses;
    }
}

