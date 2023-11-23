package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.domain.Order;
import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.dto.AdminProductDto;
import com.sitrack.sitrackbackend.dto.OrderDto;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import com.sitrack.sitrackbackend.repository.OrderRepository;
import com.sitrack.sitrackbackend.repository.ProductRepository;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class AdminService {

    private final UserAccountRepository userAccountRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    // TODO: 페이징 기능

    @Transactional(readOnly = true)
    public List<UserAccountDto> findAllUsers() {
       List<UserAccountDto> userAccountDtos = userAccountRepository.findAll()
               .stream().map(UserAccountDto::from)
               .collect(Collectors.toList());
       return userAccountDtos;
    }

    @Transactional(readOnly = true)
    public List<AdminProductDto> findAllProducts() {
        List<AdminProductDto> productDtos = productRepository.findAll()
                .stream().map(AdminProductDto::from)
                .collect(Collectors.toList());;
        return productDtos;
    }

    @Transactional(readOnly = true)
    public List<OrderDto> findAllOrders() {
        List<OrderDto> orderDtos = orderRepository.findAll()
                .stream().map(OrderDto::from)
                .collect(Collectors.toList());
        System.out.println(orderDtos);
        return orderDtos;
    }

    public String updateProduct(AdminProductDto productDto) {
         Product product = productRepository.findById(productDto.productId()).orElseThrow();
         if(productDto.productName() != null){
             product.setProductName(productDto.productName());
         }
         if(productDto.productPrice() != null){
             product.setProductPrice(productDto.productPrice());
         }
         if(productDto.productCost() != null){
             product.setProductCost(productDto.productCost());
         }
         if(productDto.supplier() != null){
             product.setSupplier(productDto.supplier().toEntity());
         }
         if(productDto.category() != null){
             product.setCategory(productDto.category().toEntity());
         }
         if(productDto.productStockQuantity() != null){
             product.setProductStockQuantity(productDto.productStockQuantity());
         }
         if(productDto.productSalesQuantity() != null){
             product.setProductSalesQuantity(productDto.productSalesQuantity());
         }
         return "상품 업데이트 완료";
    }

    public String updateOrder(OrderDto orderDto) {
        Order order = orderRepository.findById(orderDto.orderId()).orElseThrow();
        if(orderDto.orderStatus() != null){
            order.setOrderStatus(orderDto.orderStatus());
        }else{
            return "업데이트 실패";
        }
        return "주문 현황 업데이트 완료";
    }
}
