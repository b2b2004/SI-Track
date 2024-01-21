package com.sitrack.sitrackbackend.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.constant.ProductImageType;
import com.sitrack.sitrackbackend.dto.AdminProductDto;
import com.sitrack.sitrackbackend.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sitrack.sitrackbackend.domain.QCategory.category;
import static com.sitrack.sitrackbackend.domain.QProduct.product;
import static com.sitrack.sitrackbackend.domain.QProductImage.productImage;
import static com.sitrack.sitrackbackend.domain.QSupplier.supplier;
import static com.sitrack.sitrackbackend.domain.account.QUserAccount.userAccount;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Page<AdminProductDto> searchPageComplex(String searchType, String searchValue, Pageable pageable){
        List<AdminProductDto> content = getAdminProductDto(searchType, searchValue, pageable);
        Long count = getCount(searchType, searchValue);
        return new PageImpl<>(content, pageable, count);
    }

    public Page<ProductResponse> searchMainPageComplex(String searchType, String searchValue, Pageable pageable){
        List<ProductResponse> content = getProductResponse(searchType, searchValue, pageable);
        Long count = getCount(searchType, searchValue);
        return new PageImpl<>(content, pageable, count);
    }

    private Long getCount(String searchType, String searchValue) {
        Long count = queryFactory
                .select(product.count())
                .from(product)
                .where(typeCheck(searchType, searchValue))
                .fetchOne();
        return count;
    }

    public List<ProductResponse> getProductResponse(String searchType, String searchValue, Pageable pageable){
        /**
         * 필요한 컬럼만 받아서 성능 최적화
         * productName 인덱스 설정
         * Jmeter 데이터 1000, 동시 접근 2000
         * - 페이징 시 성능 avg 0.406
         * - 필요한 컬럼만 받을때 avg 0.204
         * 약 2배 성능 개선
         */
        List<ProductResponse> products = queryFactory
                .select(Projections.constructor(ProductResponse.class,
                        product.id,
                        supplier.supplierCode,
                        product.productName,
                        product.productPrice,
                        product.productDetail,
                        productImage.imagePath.concat(productImage.saveName)
                        ))
                .from(product)
                .leftJoin(product.category, category)
                .leftJoin(product.supplier, supplier)
                .leftJoin(product.productImages, productImage).on(productImage.imageType.eq(ProductImageType.valueOf("Thumbnail")))
                .where(typeCheck(searchType, searchValue))
                .offset(pageable.getOffset()) // 페이지 번호
                .limit(pageable.getPageSize()) // 페이지 사이즈
                .stream().collect(Collectors.toList());

        System.out.println(products);
        return products;
    }

    public List<AdminProductDto> getAdminProductDto(String searchType, String searchValue, Pageable pageable){

        /**
         * 필요한 컬럼만 받아서 성능 최적화
         * productName 인덱스 설정
         * Jmeter 데이터 1000, 동시 접근 2000
         * - 페이징 시 성능 avg 0.575
         * - 필요한 컬럼만 받을때 avg 0.24
         * 약 2.5배 성능 개선
         */
         List<AdminProductDto> products = queryFactory
                .select(Projections.constructor(AdminProductDto.class,
                        product.id,
                        userAccount.userId,
                        category.id,
                        category.categoryName,
                        supplier.id,
                        supplier.supplierName,
                        supplier.supplierCode,
                        product.productName,
                        product.productCost,
                        product.productPrice,
                        product.productStockQuantity,
                        product.productSalesQuantity))
                .from(product)
                .leftJoin(product.category, category)
                .leftJoin(product.supplier, supplier)
                .leftJoin(product.userAccount, userAccount)
                .where(typeCheck(searchType, searchValue))
                .offset(pageable.getOffset()) // 페이지 번호
                .limit(pageable.getPageSize()) // 페이지 사이즈
                .stream().collect(Collectors.toList());
        return products;
    }

    public Optional<Product> findById(Long productId){
        Optional<Product> pd = Optional.ofNullable(queryFactory
                .select(product)
                .from(product)
                .leftJoin(product.category, category).fetchJoin()
                .leftJoin(product.supplier, supplier).fetchJoin()
                .leftJoin(product.userAccount, userAccount).fetchJoin()
                .where(product.id.eq(productId))
                .fetchOne());

        return pd;
    }

    private BooleanExpression productNameLike(String searchValue) {
        return StringUtils.hasText(searchValue) ? product.productName.contains(searchValue) : null;
    }

    private BooleanExpression categoryNameLike(String searchValue) {
        return StringUtils.hasText(searchValue) ? product.category.categoryName.contains(searchValue) : null;
    }


    private BooleanExpression typeCheck(String searchType, String searchValue){
        if(searchType == null){
            return null;
        }

        if(searchType.equals("productName")){
            return productNameLike(searchValue);
        }else if(searchType.equals("categoryName")){
            return categoryNameLike(searchValue);
        }else{
            return null;
        }
    }
}
