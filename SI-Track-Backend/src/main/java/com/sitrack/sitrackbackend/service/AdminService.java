package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.Exception.CustomException;
import com.sitrack.sitrackbackend.domain.Category;
import com.sitrack.sitrackbackend.domain.Order;
import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.Supplier;
import com.sitrack.sitrackbackend.dto.*;
import com.sitrack.sitrackbackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.sitrack.sitrackbackend.Exception.ErrorCode.*;

@RequiredArgsConstructor
@Transactional
@Service
public class AdminService {

    private final UserAccountRepository userAccountRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;

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
        return orderDtos;
    }

    @Transactional(readOnly = true)
    public List<SupplierDto> findAllSupplier() {
        List<SupplierDto> supplierDtos = supplierRepository.findAll()
                .stream().map(SupplierDto::from)
                .collect(Collectors.toList());
        return supplierDtos;
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> findAllCategory() {
        List<CategoryDto> categoryDtos = categoryRepository.findAll()
                .stream().map(CategoryDto::from)
                .collect(Collectors.toList());
        return categoryDtos;
    }

    public String updateProduct(AdminProductDto productDto) {
         Product product = productRepository.findById(productDto.productId())
                 .orElseThrow(()-> new CustomException(PRODUCT_NOT_FOUND));
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
        Order order = orderRepository.findById(orderDto.orderId())
                .orElseThrow(()-> new CustomException(ORDER_NOT_FOUND));
        if(orderDto.orderStatus() != null){
            order.setOrderStatus(orderDto.orderStatus());
        }else{
            return "업데이트 실패";
        }
        return "주문 현황 업데이트 완료";
    }

    public String updateSupplier(SupplierDto supplierDto) {
        Supplier supplier = supplierRepository.findById(supplierDto.supplierId())
                .orElseThrow(()-> new CustomException(SUPPLIER_NOT_FOUND));

        if(!supplierDto.supplierCode().isBlank()){
            supplier.setSupplierName(supplierDto.supplierCode());
        }else{
            return "코드를 확인해주세요.";
        }

        if(!supplierDto.supplierName().isBlank()){
            supplier.setSupplierName(supplierDto.supplierName());
        }else{
            return "공급업체 이름을 확인해주세요.";
        }

        return "공급업체 정보 수정 완료";
    }

    public String updateCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.categoryId())
                .orElseThrow(()-> new CustomException(CATEGORY_NOT_FOUND));

        if(!categoryDto.categoryName().isBlank()){
            category.setCategoryName(categoryDto.categoryName());
        }else{
            return "카테고리 이름을 확인해주세요.";
        }

        return "카테고리 정보 수정 완료";
    }

    public String registerSupplier(SupplierDto supplierDto) {
        if(supplierDto.supplierCode().isBlank()){
            return "코드를 확인해주세요.";
        }
        if(supplierDto.supplierName().isBlank()){
            return "공급업체 이름을 확인해주세요.";
        }
        Supplier supplier = supplierDto.toEntity();
        supplierRepository.save(supplier);
        return "등록 성공";
    }

    public String registerCategory(CategoryDto categoryDto) {
        if(categoryDto.categoryName().isBlank()){
            return "카테고리 이름을 확인해주세요.";
        }

        Category category = categoryDto.toEntity();
        categoryRepository.save(category);
        return "등록 성공";
    }


    public String deleteSupplier(Long supplierId) {
        supplierRepository.deleteById(supplierId);
        return "삭제 성공";
    }

    public String deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return "삭제 성공";
    }
}
