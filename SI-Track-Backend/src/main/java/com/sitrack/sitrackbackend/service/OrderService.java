package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.Exception.CustomException;
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

import static com.sitrack.sitrackbackend.Exception.ErrorCode.PRODUCT_NOT_FOUND;
import static com.sitrack.sitrackbackend.Exception.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserAccountRepository userAccountRepository;

    public void save(OrderRequest orderRequest, UserAccount userAccount) {

        List<OrderItemDto> orderItemDto =  orderRequest.orderItemDtos();
        List<Product> products = new ArrayList<>();
        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderItemDto dto : orderItemDto){
           Product product = productRepository.findById(dto.productId())
                   .orElseThrow(()-> new CustomException(PRODUCT_NOT_FOUND));
           OrderItem orderItem = OrderItem.of(product, dto.quantity(), product.getProductPrice());
           products.add(product);
           orderItems.add(orderItem);
        }

        // 수량 확인 및 수량 조절
        for(Product product : products){
            for(OrderItem orderItem : orderItems){
                if(product.getId().equals(orderItem.getProduct().getId())){
                    if(product.getProductStockQuantity() < orderItem.getOrderItemQuantity()){
                        // 추후 커스텀으로 바꾸겠음.
                        throw new IndexOutOfBoundsException();
                    }else{
                        product.minusproductStockQuantity(orderItem.getOrderItemQuantity());
                    }
                }else{
                    throw new EntityNotFoundException();
                }
            }
        }

        Order order = Order.of(userAccount, orderRequest, orderItems);
        orderRepository.save(order);

        // 장바구니 확인 및 장바구니 아이템 삭제
        if(cartRepository.findCartByUserAccount(userAccount).isPresent()){
            List<CartItem> cartItems = cartItemRepository.findByCartId(userAccount.getCart().getId());

            for(CartItem cartItem : cartItems){
                for(OrderItem orderItem : orderItems){
                    if (cartItem.getProduct().getId().equals(orderItem.getProduct().getId())){
                        cartItemRepository.delete(cartItem);
                    }
                }
            }

        }
    }

    public List<OrderResponse> findByUserOrders(UserAccount userAccount) {

        UserAccount user = userAccountRepository.findByUserId(userAccount.getUserId())
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

