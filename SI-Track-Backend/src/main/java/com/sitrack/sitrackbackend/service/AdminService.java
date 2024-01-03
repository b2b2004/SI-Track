package com.sitrack.sitrackbackend.service;

import com.sitrack.sitrackbackend.Exception.CustomException;
import com.sitrack.sitrackbackend.domain.Category;
import com.sitrack.sitrackbackend.domain.Order;
import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.Supplier;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.*;
import com.sitrack.sitrackbackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.sitrack.sitrackbackend.Exception.ErrorCode.*;

@RequiredArgsConstructor
@Transactional
@Service
public class AdminService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;
    private final UserAccountRepositoryCustom userAccountRepositoryCustom;
    private final ProductRepositoryCustom productRepositoryCustom;
    private final OrderRepositoryCustom orderRepositoryCustom;
    private final SupplierRepositoryCustom supplierRepositoryCustom;

    @Transactional(readOnly = true)
    public Page<UserAccountDto> findUsersWithSearchType(String searchType, String searchValue, Pageable pageable){
        return userAccountRepositoryCustom.searchPageComplex(searchType, searchValue, pageable);
    }

    @Transactional(readOnly = true)
    public Page<AdminProductDto> findProductsWithSearchType(String searchType, String searchValue, Pageable pageable) {
        return productRepositoryCustom.searchPageComplex(searchType, searchValue, pageable);
    }

    @Transactional(readOnly = true)
    public Page<OrderDto> findOrdersWithSearchType(String searchType, String searchValue, Pageable pageable) {
        return orderRepositoryCustom.searchPageComplex(searchType, searchValue, pageable);
    }

    @Transactional(readOnly = true)
    public Page<SupplierDto> findSuppliersWithSearchType(String searchType, String searchValue, Pageable pageable) {
        return supplierRepositoryCustom.searchPageComplex(searchType, searchValue, pageable);
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

         product.setProductName(productDto.productName());
         product.setProductPrice(productDto.productPrice());
         product.setProductCost(productDto.productCost());
         product.setSupplier(productDto.supplier().toEntity());
         product.setCategory(productDto.category().toEntity());
         product.setProductStockQuantity(productDto.productStockQuantity());
         product.setProductSalesQuantity(productDto.productSalesQuantity());

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

        supplier.setSupplierName(supplierDto.supplierCode());
        supplier.setSupplierName(supplierDto.supplierName());
        return "공급업체 정보 수정 완료";
    }

    public String updateCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.categoryId())
                .orElseThrow(()-> new CustomException(CATEGORY_NOT_FOUND));
        category.setCategoryName(categoryDto.categoryName());
        return "카테고리 정보 수정 완료";
    }

    public String registerSupplier(SupplierDto supplierDto) {
        Supplier supplier = supplierDto.toEntity();
        supplierRepository.save(supplier);
        return "등록 성공";
    }

    public String registerCategory(CategoryDto categoryDto) {
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
