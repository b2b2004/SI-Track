package com.sitrack.sitrackbackend.dto;

import com.sitrack.sitrackbackend.domain.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public record OrderDto(
        Long orderId,
        String userId,
        Long totalAmount,
        String orderAddress,
        String orderStatus,
        String orderRequest,
        String recipient,
        String phoneNumber,
        LocalDateTime createdAt,
        List<OrderItemDto> orderItemDtos

) {

    public static OrderDto of(Long orderId, String userId, Long totalAmount, String orderAddress, String orderStatus, String orderRequest, String recipient, String phoneNumber, LocalDateTime createdAt, List<OrderItemDto> orderItemDtos){
        return new OrderDto(orderId, userId, totalAmount, orderAddress, orderStatus, orderRequest, recipient, phoneNumber, createdAt, orderItemDtos);
    }

    public static OrderDto from(Order entity){
        return new OrderDto(
                entity.getId(),
                entity.getUserAccount().getUserId(),
                entity.getTotalAmount(),
                entity.getOrderAddress(),
                entity.getOrderStatus(),
                entity.getOrderRequest(),
                entity.getRecipient(),
                entity.getPhoneNumber(),
                entity.getCreatedAt(),
                entity.getOrderItems()
                        .stream().map(OrderItemDto::from)
                        .collect(Collectors.toList())
        );
    }
}
