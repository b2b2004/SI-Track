package com.sitrack.sitrackbackend.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sitrack.sitrackbackend.domain.Order;
import com.sitrack.sitrackbackend.dto.AdminProductDto;
import com.sitrack.sitrackbackend.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.sitrack.sitrackbackend.domain.QOrder.order;
import static com.sitrack.sitrackbackend.domain.QProduct.product;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Page<OrderDto> searchPageComplex(String searchType, String searchValue, Pageable pageable){
        List<OrderDto> content = getOrderDto(searchType, searchValue, pageable);
        Long count = getCount(searchType, searchValue);

        return new PageImpl<>(content, pageable, count);
    }

    private Long getCount(String searchType, String searchValue) {
        Long count = queryFactory
                .select(order.count())
                .from(order)
                .where(typeCheck(searchType, searchValue))
                .fetchOne();
        return count;
    }

    public List<OrderDto> getOrderDto(String searchType, String searchValue, Pageable pageable){

        List<OrderDto> orders = queryFactory
                .select(order)
                .from(order)
                .where(typeCheck(searchType, searchValue))
                .offset(pageable.getOffset()) // 페이지 번호
                .limit(pageable.getPageSize()) // 페이지 사이즈
                .fetch()
                .stream().map(OrderDto::from)
                .collect(Collectors.toList());
        return orders;
    }

    private BooleanExpression userIdLike(String searchValue) {
        return StringUtils.hasText(searchValue) ? order.userAccount.userId.contains(searchValue) : null;
    }

    private BooleanExpression userNameLike(String searchValue) {
        return StringUtils.hasText(searchValue) ? order.userAccount.userName.contains(searchValue) : null;
    }

    private BooleanExpression typeCheck(String searchType, String searchValue){
        if(searchType == null){
            return null;
        }

        if(searchType.equals("userId")){
            return userIdLike(searchValue);
        }else if(searchType.equals("userName")){
            return userNameLike(searchValue);
        }else{
            return null;
        }
    }
}
