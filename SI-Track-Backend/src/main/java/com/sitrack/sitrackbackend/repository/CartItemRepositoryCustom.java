package com.sitrack.sitrackbackend.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sitrack.sitrackbackend.domain.QCartItem.cartItem;

@Repository
@RequiredArgsConstructor
public class CartItemRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public void deleteAllByIds(List<Long> ids){
        queryFactory.delete(cartItem)
                .where(cartItem.product.id.in(ids))
                .execute();
    }
}
