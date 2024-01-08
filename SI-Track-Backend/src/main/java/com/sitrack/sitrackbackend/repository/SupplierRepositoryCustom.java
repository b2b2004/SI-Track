package com.sitrack.sitrackbackend.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sitrack.sitrackbackend.dto.OrderDto;
import com.sitrack.sitrackbackend.dto.SupplierDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.sitrack.sitrackbackend.domain.QOrder.order;
import static com.sitrack.sitrackbackend.domain.QSupplier.supplier;

@Repository
@RequiredArgsConstructor
public class SupplierRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Page<SupplierDto> searchPageComplex(String searchType, String searchValue, Pageable pageable){
        List<SupplierDto> content = getSupplierDto(searchType, searchValue, pageable);
        Long count = getCount(searchType, searchValue);

        return new PageImpl<>(content, pageable, count);
    }

    private Long getCount(String searchType, String searchValue) {
        Long count = queryFactory
                .select(supplier.count())
                .from(supplier)
                .where(typeCheck(searchType, searchValue))
                .fetchOne();
        return count;
    }

    public List<SupplierDto> getSupplierDto(String searchType, String searchValue, Pageable pageable){

        List<SupplierDto> suppliers = queryFactory
                .select(supplier)
                .from(supplier)
                .where(typeCheck(searchType, searchValue))
                .offset(pageable.getOffset()) // 페이지 번호
                .limit(pageable.getPageSize()) // 페이지 사이즈
                .fetch()
                .stream().map(SupplierDto::from)
                .collect(Collectors.toList());
        return suppliers;
    }

    private BooleanExpression supplierNameLike(String searchValue) {
        return StringUtils.hasText(searchValue) ? supplier.supplierName.contains(searchValue) : null;
    }

    private BooleanExpression supplierCodeLike(String searchValue) {
        return StringUtils.hasText(searchValue) ? supplier.supplierCode.contains(searchValue) : null;
    }

    private BooleanExpression typeCheck(String searchType, String searchValue){
        if(searchType == null){
            return null;
        }

        if(searchType.equals("supplierName")){
            return supplierNameLike(searchValue);
        }else if(searchType.equals("supplierCode")){
            return supplierCodeLike(searchValue);
        }else{
            return null;
        }
    }
}
