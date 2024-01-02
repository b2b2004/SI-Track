package com.sitrack.sitrackbackend.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sitrack.sitrackbackend.dto.AdminProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.sitrack.sitrackbackend.domain.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Page<AdminProductDto> searchPageComplex(String searchType, String searchValue, Pageable pageable){
        List<AdminProductDto> content = getAdminProductDtoDto(searchType, searchValue, pageable);
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

    public List<AdminProductDto> getAdminProductDtoDto(String searchType, String searchValue, Pageable pageable){

        List<AdminProductDto> products = queryFactory
                .select(product)
                .from(product)
                .where(typeCheck(searchType, searchValue))
                .offset(pageable.getOffset()) // 페이지 번호
                .limit(pageable.getPageSize()) // 페이지 사이즈
                .fetch()
                .stream().map(AdminProductDto::from)
                .collect(Collectors.toList());
        return products;
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
