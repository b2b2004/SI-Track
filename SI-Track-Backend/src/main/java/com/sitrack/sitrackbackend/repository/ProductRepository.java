package com.sitrack.sitrackbackend.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.sitrack.sitrackbackend.domain.Product;
import com.sitrack.sitrackbackend.domain.QProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.Optional;

public interface ProductRepository extends
        JpaRepository<Product,Long>,
        QuerydslPredicateExecutor<Product>, // 기본 검색 기능 추가
        QuerydslBinderCustomizer<QProduct>{

    @Override
    default void customize(QuerydslBindings bindings, QProduct root){
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.productName);
        bindings.bind(root.productName).first(StringExpression::containsIgnoreCase);
    }
}